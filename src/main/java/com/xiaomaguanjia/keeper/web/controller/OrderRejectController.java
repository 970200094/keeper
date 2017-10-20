package com.xiaomaguanjia.keeper.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoma.basicservice.contract.CategoryService;
import com.xiaoma.basicservice.contract.ConfigService;
import com.xiaoma.basicservice.contract.LightKeeperApplyService;
import com.xiaoma.basicservice.contract.ModifyApplyOrderService;
import com.xiaoma.basicservice.contract.OrderMessageLogService;
import com.xiaoma.basicservice.contract.OrderModifyService;
import com.xiaoma.basicservice.contract.OrderService;
import com.xiaoma.basicservice.contract.UserService;
import com.xiaoma.basicservice.contract.coupons.ActiveInfoService;
import com.xiaoma.basicservice.contract.lightkeeper.ServiceFrequencyService;
import com.xiaoma.basicservice.entity.CategoryEntity;
import com.xiaoma.basicservice.entity.ConfigEntity;
import com.xiaoma.basicservice.entity.LightKeeperApply;
import com.xiaoma.basicservice.entity.OrderEntity;
import com.xiaoma.basicservice.entity.OrderMessageEntity;
import com.xiaoma.basicservice.entity.OrderModifyEntity;
import com.xiaoma.basicservice.entity.UserEntity;
import com.xiaoma.basicservice.entity.coupons.ActiveInfoEntity;
import com.xiaoma.basicservice.entity.lightkeeper.ServiceFrequencyEntity;
import com.xiaoma.util.AssertUtils;
import com.xiaomaguanjia.keeper.bean.RejectBean;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

@Controller
@RequestMapping(value = "reject")
public class OrderRejectController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderModifyService modifyService;
	@Autowired
	private ModifyApplyOrderService modifyApplyOrderService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ActiveInfoService activeInfoService;
	@Autowired
	private LightKeeperApplyService applyService;
	@Autowired
	private ServiceFrequencyService serviceFrequencyService;
	@Autowired
	private OrderMessageLogService orderMessageLogService;

	@RequestMapping("details")
	public @ResponseBody
	BaseJsonVo getRejectDatils(long orderId) {
		Long userId = RequestUtils.getUserId();
		OrderModifyEntity orderModifyEntity = modifyService.getDetails(userId,
				orderId);
		if(orderModifyEntity==null){
			return BaseJsonVo.paramError("参数不正确");
		}
		OrderEntity orderEntity = orderService.get(orderModifyEntity
				.getOrderId());
		RejectBean bean = new RejectBean();
		bean.setCommtent(orderModifyEntity.getComment());
		bean.setId(orderModifyEntity.getId());
		bean.setExplanation(orderModifyEntity.getExplanation());
		bean.setStatus(orderModifyEntity.getState());
		bean.setServiceTime(orderModifyEntity.getServiceTtime());
		bean.setAddress(orderEntity.getAddress());
		bean.setPhone(orderEntity.getPhone());
		List<ConfigEntity> configEntities = configService.getValidList(
				"reject_reason", orderModifyEntity.getReasonId() + "");
		if (configEntities != null && configEntities.size() != 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < configEntities.size(); i++) {
				ConfigEntity entity = configEntities.get(i);
				if (i != 0) {
					sb.append(",");
				}
				sb.append(entity.getConfigvalue());
			}
			bean.setReason(sb.toString());
		}
		bean.setPeopleSize(orderModifyEntity.getKeeper_ids().split(",").length);
		UserEntity usrEntity = userService.getByUserId(orderEntity.getUserId()
				+ "");
		
		String realName=usrEntity.getRealName();
        if(realName==null||realName.length()==0||realName.trim().equals("")){
       	 realName="*******";
        }
		bean.setName(realName);

		Long applyId = orderEntity.getApply_id();
		int activityId = orderEntity.getActivityId();
		int category_id = orderEntity.getCategoryId();
		String name = null;
		if (applyId != null && applyId != 0) {
			LightKeeperApply app = applyService.get(applyId);
			if (app != null) {
				ServiceFrequencyEntity serviceFrequencyEntity = serviceFrequencyService
						.get(app.getServiceType());
				if (serviceFrequencyEntity != null) {
					name = serviceFrequencyEntity.getName();
				}

			}

		} else if (activityId != 0) {
			ActiveInfoEntity infoEntity = activeInfoService
					.get(activityId);
			if (infoEntity != null) {
				name = infoEntity.getActiveName();
			}

		} else {
			CategoryEntity categoryEntity = categoryService.get(category_id);
			if (categoryEntity != null) {
				name = categoryEntity.getName();
			}
		}
		bean.setServiceName(name);
		
		ArrayList<String> userLable=new ArrayList<>();
		
		
		if(usrEntity.getIsMark()==1){
			userLable.add("重要客户");
		}
		if(userService.isVip(usrEntity.getId())){
			userLable.add("卡户");
		}
		if(usrEntity.getBlacklist()==1){
			userLable.add("黑户");
		}
		
		if(usrEntity.getIs_buy()==1){
			userLable.add("代购");
		}
		if(usrEntity.getVipLevel()==1){
			userLable.add("vip");
		}
		
		bean.setUserLable(userLable);
		
		
		OrderMessageEntity orderMessageEntity=orderMessageLogService.getOrderMessageEntity(orderEntity.getId());
		if(orderMessageEntity==null){
			orderMessageEntity=new OrderMessageEntity();
			orderMessageEntity.setOrder_id(orderEntity.getId());
			orderMessageEntity.setOpen_state(1);
			orderMessageEntity.setOpen_time(new Date());
			orderMessageEntity.setKeeper_id(userId);
			orderMessageLogService.save(orderMessageEntity);
		}
		else{
			if(orderMessageEntity.getOpen_state()==0){
				orderMessageEntity.setOpen_state(1);
				orderMessageLogService.update(orderMessageEntity);
			}
		
		}
		
		return BaseJsonVo.success(bean);

	}

	@RequestMapping("list")
	public @ResponseBody
	BaseJsonVo getRejectList(@RequestParam int page,@RequestParam int type) {

		Long userId = RequestUtils.getUserId();

		List<OrderModifyEntity> list = modifyService.getAll(userId,type ,page);
		if (list == null || list.size() == 0) {
			return BaseJsonVo.empty();
		}
		List<RejectBean> rejectBeans = new ArrayList<>();
		int count =modifyService.getOrderCount(userId, type);
		for (OrderModifyEntity orderModifyEntity : list) {

			OrderEntity orderEntity = orderService.get(orderModifyEntity
					.getOrderId());
			RejectBean bean = new RejectBean();
			bean.setOrderId(orderModifyEntity
					.getOrderId());
			bean.setCommtent(orderModifyEntity.getComment());
			bean.setId(orderModifyEntity.getId());
			bean.setExplanation(orderModifyEntity.getExplanation());
			bean.setStatus(orderModifyEntity.getState());
			bean.setServiceTime(orderModifyEntity.getServiceTtime());
			bean.setAddress(orderEntity.getAddress());
			bean.setPhone(orderEntity.getPhone());
			List<ConfigEntity> configEntities = configService.getValidList(
					"reject_reason", orderModifyEntity.getReasonId() + "");

			if (configEntities != null && configEntities.size() != 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < configEntities.size(); i++) {
					ConfigEntity entity = configEntities.get(i);
					if (i != 0) {
						sb.append(",");
					}
					sb.append(entity.getConfigvalue());
				}
				bean.setReason(sb.toString());
			}

			bean.setPeopleSize(orderModifyEntity.getKeeper_ids().split(",").length);
			UserEntity usrEntity = userService.getByUserId(orderEntity
					.getUserId() + "");
			
			
			if(usrEntity!=null){
				ArrayList<String> userLable=new ArrayList<>();
				bean.setUserLable(userLable);
				String realName=usrEntity.getRealName();
			      if(realName==null||realName.length()==0||realName.trim().equals("")){
			        	 realName="*******";
			       }
				bean.setName(realName);

				if(usrEntity.getIsMark()==1){
					userLable.add("重要客户");
				}
				if(userService.isVip(usrEntity.getId())){
					userLable.add("卡户");
				}
				if(usrEntity.getBlacklist()==1){
					userLable.add("黑户");
				}
				
				if(usrEntity.getIs_buy()==1){
					userLable.add("代购");
				}
				if(usrEntity.getVipLevel()==1){
					userLable.add("vip");
				}
			}
			  
			
		
			
			
			Long applyId = orderEntity.getApply_id();
			int activityId = orderEntity.getActivityId();
			long category_id = orderEntity.getCategoryId();
			String name = null;
			if (applyId != null && applyId != 0) {
				LightKeeperApply app = applyService.get(applyId);
				if (app != null) {
					ServiceFrequencyEntity serviceFrequencyEntity = serviceFrequencyService
							.get(app.getServiceType());
					if (serviceFrequencyEntity != null) {
						name = serviceFrequencyEntity.getName();
					}

				}

			} else if (activityId != 0) {
				ActiveInfoEntity infoEntity = activeInfoService
						.get(activityId);
				if (infoEntity != null) {
					name = infoEntity.getActiveName();
				}

			} else {
				CategoryEntity categoryEntity = categoryService
						.get(category_id);
				if (categoryEntity != null) {
					name = categoryEntity.getName();
				}
			}
			if (name != null) {
				bean.setServiceName(name);
			}
			
			
			int openState=0;
			OrderMessageEntity orderMessageEntity=orderMessageLogService.getOrderMessageEntity(orderEntity.getId());
			if(orderMessageEntity!=null){
				openState=orderMessageEntity.getOpen_state();
			}
			
			bean.setOpen_state(openState);
			
		
			rejectBeans.add(bean);

		}
        HashMap<String, Object> map=new HashMap<String, Object> ();
        map.put("count", count);
        map.put("orderVo", rejectBeans);
		return BaseJsonVo.success(map);

	}

	@RequestMapping("prepare")
	@ResponseBody
	public BaseJsonVo getOrderRejectReason(long orderId) {
		OrderEntity order = orderService.get(orderId);
		AssertUtils.notNull(order, "订单不存在");
		if (order.getServicesTime().getTime() - System.currentTimeMillis() <= 24 * 60 * 60 * 1000) {
			return BaseJsonVo.paramError("距离服务时间小1天不允许改单");
		} else if (order.getState() == OrderEntity.STATE_WAIT) {
			List<ConfigEntity> list=configService.getValidList("reject_reason_desc", "change");
			HashMap<String, Object> map=new HashMap<>();
			map.put("reason_text", (modifyService.getRejectReasonText()));
			if(list!=null&&list.size()!=0){
				map.put("reason_time", list.get(0).getConfigvalue());
			}
			
			return BaseJsonVo.success(map);
		} else {
			return BaseJsonVo.paramError("订单非待服务状态不允许改单");
		}
	}

	@RequestMapping("submit")
	@ResponseBody
	public BaseJsonVo submitOrderRejectApply(Long orderId, Integer reasonId, String explanation) {
		Long keeperId = RequestUtils.getUserId();
		OrderEntity order = orderService.get(orderId);
		AssertUtils.notNull(order, "订单不存在");
		if (order.getServicesTime().getTime() - System.currentTimeMillis() <= 24 * 60 * 60 * 1000) {
			return BaseJsonVo.paramError("距离服务时间小1天不允许改单");
	   } else if (order.getState() == OrderEntity.STATE_WAIT) {
			int id = modifyService.submitRejectApply(keeperId, reasonId, explanation, orderId);
			return BaseJsonVo.success(id);
	   } else {
		  return BaseJsonVo.paramError("订单非待服务状态不允许改单");
	   }

//		long id = modifyApplyOrderService.saveModifyOrderForKeeper(keeperId, reasonId, explanation, orderId);
	}
	
}