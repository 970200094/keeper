package com.xiaomaguanjia.keeper.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaomaguanjia.message.contract.entity.PushUserLogEntity;
import com.xiaomaguanjia.message.contract.service.UserPushService;
import com.xiaomaguanjia.message.contract.vo.ClientPushVo;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.basicservice.contract.OrderService;
import com.xiaoma.basicservice.contract.image.OrderImageService;
import com.xiaoma.basicservice.contract.task.PushConfigService;
import com.xiaoma.basicservice.entity.OrderEntity;
import com.xiaoma.basicservice.entity.image.OrderImageEntity;
import com.xiaoma.basicservice.entity.task.PushConfigEntity;
import com.xiaoma.basicservice.vo.MapList;
import com.xiaoma.basicservice.vo.OrderOfKeeperVo;
import com.xiaoma.p4jtools.Config;
import com.xiaomaguanjia.keeper.config.CommonConstant;
import com.xiaomaguanjia.keeper.service.ImageService;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

@RestController
@RequestMapping(value = "orders")
public class OrderController extends BaseController {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService; 
	@Autowired
	private ImageService imageService;
	@Autowired
	private OrderImageService orderImageService;
	@Autowired
	private UserPushService userPushService;
	@Autowired
	private PushConfigService pushConfigService;


	@RequestMapping(value = "list")
	public BaseJsonVo getList(@RequestParam(required = false) Integer status,
			@RequestParam(required = false) String date,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String group) {
		Long userId = RequestUtils.getUserId();
		Integer start = 0;
		if (page != null && page > 0) {
			start = (page - 1) * CommonConstant.PAGE_SIZE;
		}
		if (limit == null || limit < 1) {
			limit = CommonConstant.PAGE_SIZE;
		}
		// monthly order
		DateTime startDate;
		DateTime endDate;
		if (date != null && date.matches("20\\d{4}")) {
			startDate = new DateTime(Integer.valueOf(date.substring(0, 4)),
					Integer.valueOf(date.substring(4, 6)), 1, 0, 0);
			endDate = startDate.plusMonths(1);
		} else if (status != null && status == 1) {
			// block, after today
			DateTime dateTime = new DateTime();
			startDate = new DateTime(dateTime.getYear(),
					dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0);
			endDate = dateTime.plusYears(10);
		} else if (status != null && status == 4) {
			// pending, before today
			DateTime dateTime = new DateTime();
			startDate = dateTime.plusYears(-10);
			endDate = new DateTime(dateTime.getYear(),
					dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 23, 59);
		} else {
			// ready, all
			DateTime dateTime = new DateTime();
			dateTime = new DateTime(dateTime.getYear(),
					dateTime.getMonthOfYear(), 1, 0, 0);
			startDate = dateTime;
			endDate = dateTime.plusMonths(1);
		}
		LOG.debug("{} - {}", startDate.toLocalDateTime().toString(), endDate
				.toLocalDateTime().toString());
		MapList orderList = orderService.getListByKeeper(userId, status,
				startDate.toDate(), endDate.toDate(), start, limit);
		Map<String, Object> result = new HashMap<>();
		result.put("page", page);
		// group by month
		if (Boolean.valueOf(group)) {
			Map<String, MapList> groupList = new HashMap<>();
			for (Map<String, Object> objectMap : orderList) {
				String serviceDate = Integer.valueOf(objectMap
						.get("serviceDate").toString().substring(5, 7))
						+ "月";
				if (groupList.containsKey(serviceDate)) {
					groupList.get(serviceDate).add(objectMap);
				} else {
					MapList mapList = new MapList();
					mapList.add(objectMap);
					groupList.put(serviceDate, mapList);
				}
			}
			List<Object> groupList2 = new ArrayList<>();
			for (String key : groupList.keySet()) {
				Map<String, Object> item = new HashMap<>();
				item.put("month", key);
				item.put("data", groupList.get(key));
				groupList2.add(item);
			}
			result.put("orders", groupList2);
		} else {
			result.put("orders", orderList);
		}
		return BaseJsonVo.success(result);
	}

	@RequestMapping(value = "block")
	public BaseJsonVo block(@RequestParam(required = false) String date,
			@RequestParam(required = false) Integer page) {
		Long userId = RequestUtils.getUserId();
		Integer start = 0;
		if (page != null && page > 0) {
			start = (page - 1) * CommonConstant.PAGE_SIZE;
		}
		DateTime dateTime = new DateTime();
		DateTime startDate = new DateTime(dateTime.getYear(),
				dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0);
		DateTime endDate = dateTime.plusYears(10);
		LOG.debug("{} - {}", startDate.toLocalDateTime().toString(), endDate
				.toLocalDateTime().toString());
		MapList orderList = orderService.getListByKeeper(userId, 1,
				startDate.toDate(), endDate.toDate(), start,
				CommonConstant.PAGE_SIZE);
		return BaseJsonVo.success(orderList);
	}

	@RequestMapping(value = "pending")
	public @ResponseBody BaseJsonVo pending(@RequestParam(required = false) int  type,
			@RequestParam  Integer page) {
		Long userId = RequestUtils.getUserId();
		Map<String, Object> map=orderService.getPendingServiceCount(userId, type, page);
		
		return BaseJsonVo.success(map);
	}

	@RequestMapping(value = "complete")
	public @ResponseBody BaseJsonVo complete(@RequestParam(required = false) int type,
			@RequestParam  Integer page) {
		Long userId = RequestUtils.getUserId();
		
		return BaseJsonVo.success(orderService.getCompletedCount(userId, type, page));
	}

	@RequestMapping(value = "nocomplete")
	public @ResponseBody BaseJsonVo nocomplete( @RequestParam Integer page) {
		Long userId = RequestUtils.getUserId();
		return BaseJsonVo.success(orderService.getNoCompletedCount(userId, page));
	}
	
	
	
	@RequestMapping(value = "today")
	 public @ResponseBody BaseJsonVo today() {
		Long userId = RequestUtils.getUserId();
		
		Map<String, Object> map=orderService.getTodayCount(userId);

		return BaseJsonVo.success(map);
	}

	@RequestMapping(value = "info")
	public BaseJsonVo getInfo(@RequestParam Long orderId) {
		
		Long keeperId = RequestUtils.getUserId();
		OrderOfKeeperVo orderVo = orderService
				.getKeeperOrder(keeperId, orderId);
		try {
			PushUserLogEntity entity = userPushService.getPushId(orderId);
			if (entity != null) {
//				// orderVo.setIsCommit(3); //有log 日志算是求评价了
				orderVo.setEnableRequireCommentButton(false);
			} else {
				if (orderVo.getIsCommit() == OrderEntity.NOT_COMMENT) {
					orderVo.setEnableRequireCommentButton(true);
				}
			}
		} catch (Exception e) {
			LOG.info("message=="+e.getMessage());
		}
		return BaseJsonVo.success(orderVo);

	}

	@RequestMapping(value = "start")
	public BaseJsonVo start(Long orderId) {
		if(orderId==null||orderId==0){
			return BaseJsonVo.paramError("订单id不存在");
		}
		orderService.startService(orderId);
		return BaseJsonVo.success();
	}

	@RequestMapping(value = "requireComment")
	public BaseJsonVo requireComment(Long orderId) {
		if(orderId==null||orderId==0){
			return BaseJsonVo.paramError("订单id不存在");
		}
		Long keeperId = RequestUtils.getUserId();
		final long id=orderId;
		final long userId = orderService.requireComment(keeperId, orderId);
	
        new Thread(new Runnable() {
			@Override
			public void run() {
				sendPushComment(userId, id);
				
			}
		}).start();
		return BaseJsonVo.success();
	}
	
	@RequestMapping(value = "finish")
	public BaseJsonVo finish(@RequestParam long orderId){
		if(orderId==0){
			return BaseJsonVo.paramError("订单id不对");
		}
		OrderEntity orderVo=orderService.get(orderId);
		if(orderVo.getState()!=2){
			orderService.finish(orderVo.getId(), orderVo.getOvertimePrice(),
					null, orderVo.getUserId(),
					orderVo.getBalancePrice(), orderVo.getOfflinePrice(),
					orderVo.getRemarks(), "");
			 return BaseJsonVo.success();
		}
		return BaseJsonVo.paramError("订单状态不对");


	}

	@RequestMapping(value = "uploadfile")
	public BaseJsonVo uploadFile(
			@RequestParam(value = "ph_file") MultipartFile ph_file,
			@RequestParam(value = "order_id") long order_id) {
		if(ph_file==null){
			return BaseJsonVo.paramError("文件不存在");
		}
		if(order_id==0){
			return BaseJsonVo.paramError("订单id参数错误");
		}
		saveImage(ph_file, order_id, null);
		List<OrderImageEntity> list = orderImageService.getList(order_id);
		if(list==null||list.size()==0){
			return BaseJsonVo.paramError("上传失败");
		}
		
		return BaseJsonVo.success(list);
	}

	private void saveImage(MultipartFile ph_file, long order_id,
			String image_desc) {
		if (ph_file == null || ph_file.getSize() == 0)
			return;
		String image_path = imageService.uploadOrderImage(ph_file);
		if (image_path == null || image_path.equals(""))
			return;
		OrderImageEntity entity = new OrderImageEntity();
		entity.setCre_time(new Date());
		entity.setImage_desc(image_desc);
		entity.setImage_path(image_path);
		entity.setOrder_id(order_id);
		orderImageService.save(entity);
	}

	/**
	 * 推送
	 * 
	 * @param userId
	 */
	private void sendPushComment(long userId, long orderId) {
		PushConfigEntity pushConfigEntity = pushConfigService.get(Config
				.value("push_comment_id"));
		if (pushConfigEntity != null) {
			List<Long> userIds = new ArrayList<>();
			userIds.add(userId);
			ClientPushVo<?> vo = new ClientPushVo<>();
			vo.setTitle("求评价");
			vo.setContent("(亲爱的主人，服务已经完成啦，管家不易，给个好评鼓励一下管家吧~您的认可是我们前进的动力！）");
			vo.setType(60);
			userPushService.pushUser(userIds, vo, orderId);

		}
	}



}