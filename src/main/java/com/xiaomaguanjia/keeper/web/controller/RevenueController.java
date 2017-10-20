package com.xiaomaguanjia.keeper.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoma.basicservice.contract.ConfigService;
import com.xiaoma.basicservice.contract.keeperrank.KeeperCardBalanceService;
import com.xiaoma.basicservice.contract.revenue.RevenueService;
import com.xiaoma.basicservice.contract.wage.WageManagementService;
import com.xiaoma.basicservice.vo.VipLevelVo;
import com.xiaoma.basicservice.vo.keeper.KeeperCardBalanceVo;
import com.xiaoma.basicservice.vo.keeper.KeeperRevenueVo;
import com.xiaoma.basicservice.vo.wage.WageManagementVo;
import com.xiaomaguanjia.keeper.bean.KeeperCardBean;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.form.RevenueForm;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

@Controller("RevenueControllerV2.0")
@RequestMapping(value = "/revenue/*")
public class RevenueController extends BaseController {



	@Autowired
	private RevenueService revenueService;
	@Autowired
	private KeeperCardBalanceService keeperCardBalanceService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private WageManagementService wageManagementService;


	@RequestMapping(value = "history")
	public @ResponseBody
	BaseJsonVo getRevenue(@ModelAttribute RevenueForm revenueForm) {

		Long userId = RequestUtils.getUserId();
		if(revenueForm.getKeeperId()!=0){
			userId=revenueForm.getKeeperId();
		}

		KeeperRevenueVo keeperRevenueVo = revenueService.getRevenue(
				revenueForm.getRevenue_month(), userId);
		if (keeperRevenueVo == null) {
			return BaseJsonVo.empty();
		}
		return BaseJsonVo.success(keeperRevenueVo);

	}

	@RequestMapping(value = "recharge/history")
	public @ResponseBody
	BaseJsonVo getRecharge(@ModelAttribute RevenueForm revenueForm) {

		Long userId = RequestUtils.getUserId();
		if(revenueForm.getKeeperId()!=0){
			userId=revenueForm.getKeeperId();
		}
		List<KeeperCardBalanceVo> keeperCardBalanceVos = keeperCardBalanceService
				.getKeeperCardBalanceVo(revenueForm.getRevenue_month(), userId);
		if (keeperCardBalanceVos == null || keeperCardBalanceVos.size() == 0) {
			return BaseJsonVo.empty();
		}
		int cardCount = 0;
		double countPrice = 0;
		double commissionCount = 0;
		List<KeeperCardBean> keeperCardBeans = new ArrayList<>();
		for (KeeperCardBalanceVo keeperCardBalanceVo : keeperCardBalanceVos) {

			int peopleNumber =keeperCardBalanceService.getHouseKeeperNumber(keeperCardBalanceVo.getId()+"", revenueForm.getRevenue_month());
			KeeperCardBean keeperCardBean = new KeeperCardBean();
			keeperCardBean.setId(keeperCardBalanceVo.getId());
			keeperCardBean.setPeopleNumber(peopleNumber);
			keeperCardBean
					.setCardBalance(keeperCardBalanceVo.getCard_balance());
			keeperCardBean.setCardRate(keeperCardBalanceVo.getCard_rate());
			keeperCardBean
					.setConfirmTime(keeperCardBalanceVo.getConfirm_time());
			keeperCardBean.setBalance(keeperCardBalanceVo.getBalance());
			keeperCardBeans.add(keeperCardBean);
			List<VipLevelVo> vipLevelVos = configService.getValid(
					VipLevelVo.class, ConfigService.VIP_LEVEL,
					keeperCardBalanceVo.getRecharge_id() + "");
			if (vipLevelVos != null && vipLevelVos.size() != 0) {
				for (VipLevelVo vipLevelVo : vipLevelVos) {
					keeperCardBean.setCardName(vipLevelVo.getName());
				}
			}
			cardCount = cardCount + 1;
			countPrice = keeperCardBalanceVo.getCard_balance() + countPrice;
			commissionCount = keeperCardBalanceVo.getBalance()
					+ commissionCount;
		}
		HashMap<Object, Object> cardMap = new HashMap<>();
		cardMap.put("keeperCardBeans", keeperCardBeans);
		cardMap.put("cardCount", cardCount);
		cardMap.put("countPrice", countPrice);
		cardMap.put("commissionCount", commissionCount);
		return BaseJsonVo.success(cardMap);

	}

	@RequestMapping(value = "wages/manage")
	public @ResponseBody
	BaseJsonVo getWagesManage(@ModelAttribute RevenueForm revenueForm) {
		// 工资配置可查数据 从数据配置可数据
		Long userId = RequestUtils.getUserId();
		WageManagementVo vo=wageManagementService.getSameMonthAndLastMoth(revenueForm.getRevenue_month(), String.valueOf(userId));
		return BaseJsonVo.success(vo);
	}
	@RequestMapping(value = "available/month")
	public @ResponseBody BaseJsonVo getAvailableMonth() {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12); //查询上一个上一个
		Long value=calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH, 11);
		return BaseJsonVo.success(value, calendar.getTimeInMillis());

	}


}
