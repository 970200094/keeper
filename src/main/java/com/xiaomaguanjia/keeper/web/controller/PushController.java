package com.xiaomaguanjia.keeper.web.controller;

import java.util.Date;

import com.xiaomaguanjia.message.contract.service.KeeperPushService;
import com.xiaomaguanjia.message.contract.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.contract.keeper.KeeperDeviceService;
import com.xiaoma.basicservice.contract.push.PushKeeperContentSerivce;
import com.xiaoma.basicservice.entity.keeper.KeeperDeviceEntity;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.form.BindForm;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;
/**
 * 推送接口 Created by wangfudong on 2015/9/14.
 */
@Controller("pushControllerv2.0")
@RequestMapping(value = "/push/*")
public class PushController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PushController.class);

	@Autowired
	private KeeperDeviceService keeperDeviceService;
	@Autowired
	private PushKeeperContentSerivce pushKeeperContentSerivce;
	@Autowired
	private KeeperPushService keeperPushService;
	@Autowired
	private HouseKeeperService houseKeeperService;

	@RequestMapping("bind")
	public @ResponseBody
	BaseJsonVo bindKeeperDevice(@ModelAttribute BindForm form) {
		Long userId = RequestUtils.getUserId();
		KeeperDeviceEntity entity = new KeeperDeviceEntity();

		entity.setAddDate(new Date());
		entity.setImei(form.getImei());
		entity.setKeeperId(userId);
		entity.setPlatform(form.getPlatform());
		entity.setVersion(form.getVersion());
		entity.setSysVersion(form.getSysversion());

		entity.setUa(form.getUa());
		if (form.getPlatform() == 1) {
			entity.setToken(form.getToken());
		} else if (form.getPlatform() == 2) {
			entity.setClientId(form.getClientId());
		}

		Date now = new Date();
		entity.setAddDate(now);
		entity.setEditDate(now);

		keeperDeviceService.bind(entity);
		return BaseJsonVo.success();
	}

	@RequestMapping("details")
	@ResponseBody
	BaseJsonVo getDetails(@RequestParam long id) {
		ResultVo resultVo = keeperPushService.updateKeeperPushLogOpenState(id);
		if (resultVo != null) {
			return BaseJsonVo.success(resultVo);
		} else {
			return BaseJsonVo.empty();
		}
	}
}