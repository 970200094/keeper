package com.xiaomaguanjia.keeper.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.xiaomaguanjia.message.contract.entity.PushKeeperLogEntity;
import com.xiaomaguanjia.message.contract.service.KeeperPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoma.basicservice.contract.ConfigService;
import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.contract.OrderService;
import com.xiaoma.basicservice.contract.keeperrank.KeeperRankService;
import com.xiaoma.basicservice.entity.ConfigEntity;
import com.xiaoma.basicservice.entity.keeperrank.KeeperRanVo;
import com.xiaoma.basicservice.vo.HouseKeeperHomepageVo;
import com.xiaoma.p4jtools.Config;
import com.xiaoma.p4jtools.http.UrlGetContent;
import com.xiaomaguanjia.keeper.enums.PushType;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;
import com.xiaomaguanjia.keeper.web.vo.HouseKeeperAvailableVo;
import com.xiaomaguanjia.keeper.web.vo.HouseKeeperScheduleVo;
import com.xiaomaguanjia.keeper.web.vo.Schedule;
import com.xiaomaguanjia.keeper.web.vo.TimeVo;

/**
 */
@Controller("keeperControllerV2.0")
@RequestMapping(value = "/keeper/*")
public class KeeperController extends BaseController {

	private static final Logger log = LoggerFactory
			.getLogger(KeeperController.class);
	@Autowired
	private HouseKeeperService houseKeeperService;
	@Autowired
	private KeeperRankService KeeperRankService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private KeeperPushService keeperPushService;

	@RequestMapping(value = "details")
	public @ResponseBody BaseJsonVo  getDetails() {
		Map<String, Object> result = new HashMap<String, Object>();
		Long userId = RequestUtils.getUserId();
		HouseKeeperHomepageVo homepageVo=houseKeeperService.getHouseKeeperHomepageVos(userId);
	    if(homepageVo==null){
	    	return BaseJsonVo.empty();
	    }
	    else{
	    	KeeperRanVo KeeperRanVo=KeeperRankService.getKeeperRankDetail(userId);
	    	if(KeeperRanVo!=null){
	    		homepageVo.setLevel(KeeperRanVo.getResult_level());
	    		homepageVo.setHouseKeeperLevel(KeeperRanVo.getResult_level_desc());
	    	}
	    	String cardNumber=homepageVo.getCard_num().replaceAll("(\\d{4})\\d{10}(\\w{4})","$1********$2");
	    	homepageVo.setCard_num(cardNumber);
	    	ConfigEntity configEntity=configService.getValidList("houser_keeper", homepageVo.getGender()).get(0);
	    	String[] bgUrl=configEntity.getConfigvalue().split("\\|");
	    	int random=(int) (Math.random()*2);
	    	homepageVo.setBackgroundUrl(bgUrl[random]);
	    	homepageVo.setComplete(homepageVo.getComplete()+40);
	    	homepageVo.setHeadStatrs(configService.getValidList("houser_keeper","head_stars").get(0).getConfigvalue());
	    	result.put("homePage", homepageVo);
	    	HashMap<String, Object> params=new HashMap<>();
	    	params.put("passport", "123456");
	    	params.put("keeperId", userId);;
	    	String json=UrlGetContent.getContent(Config.value("node.plan.url")+"keepers/push", params, "utf-8");//TODO
//	    	String json=UrlGetContent.getContent("http://123.57.176.67:3000/keepers/push", params, "utf-8");

	    	Gson gson = new Gson();
	    	List<HouseKeeperScheduleVo> houVos = gson.fromJson(json,
					new TypeToken<List<HouseKeeperScheduleVo>>() {
					}.getType());
	    	if(houVos!=null&&houVos.size()!=0){
	    		result.put("scheduleTime", getHouseKeeperAvailableVos(houVos));
	    	}
	    	return BaseJsonVo.success(result);
	    	
	    	
	    }
	}
	private  LinkedHashMap<String, String> initHouseKeeperSerivceTime() {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("8.0", "8:00");
		hashMap.put("8.5", "8:30");
		hashMap.put("9.0", "9:00");
		hashMap.put("9.5", "9:30");
		hashMap.put("10.0", "10:00");
		hashMap.put("10.5", "10:30");
		hashMap.put("11.0", "11:00");
		hashMap.put("11.5", "11:30");
		hashMap.put("12.0", "12:00");
		hashMap.put("12.5", "12:30");
		hashMap.put("13.0", "13:00");
		hashMap.put("13.5", "13:30");
		hashMap.put("14.0", "14:00");
		hashMap.put("14.5", "14:30");
		hashMap.put("15.0", "15:00");
		hashMap.put("15.5", "15:30");
		hashMap.put("16.0", "16:00");
		hashMap.put("16.5", "16:30");
		hashMap.put("17.0", "17:00");
		hashMap.put("17.5", "17:30");
		hashMap.put("18.0", "18:00");
		hashMap.put("18.5", "18:30");


		return hashMap;
	}
	private List<HouseKeeperAvailableVo> getHouseKeeperAvailableVos(List<HouseKeeperScheduleVo> houVos)
	{
		
		LinkedHashMap<String, String> hashMap = initHouseKeeperSerivceTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
		List<HouseKeeperAvailableVo> hlTimeVos = new ArrayList<HouseKeeperAvailableVo>();
		int i = 0;
		for (HouseKeeperScheduleVo houseKeeperPlanTimeVo : houVos) {
			HouseKeeperAvailableVo time = new HouseKeeperAvailableVo();
			if (i == 0) {
				time.setData("今  天");
			} else if (i == 1) {
				time.setData("明  天");
			} else {
				time.setData(simpleDateFormat.format(houseKeeperPlanTimeVo
						.getDate()));
			}
			List<Schedule> list = houseKeeperPlanTimeVo.getSchedules();
			LinkedHashMap<String, String> schedules = new LinkedHashMap<String, String>();
			if(list!=null&&list.size()!=0){
				for (Schedule schedule : list) {
					scheduleMap(hashMap, schedules, schedule);
				}
				time.setTimesVo(availableMap(schedules));
			}
			else{
				time.setTimesVo(noAvailableMap(schedules));
			}
			
			
			
			hlTimeVos.add(time);
			i=i+1;

		}
		return hlTimeVos;
		
	}
	private  LinkedHashMap<String, String> initHouseKeeperNoAvailable() {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

		hashMap.put("8:00", "0");
		hashMap.put("8:30", "0");
		hashMap.put("9:00", "0");
		hashMap.put("9:30", "0");
		hashMap.put("10:00", "0");
		hashMap.put("10:30", "0");
		hashMap.put("11:00", "0");
		hashMap.put("11:30", "0");
		hashMap.put("12:00", "0");
		hashMap.put("12:30", "0");
		hashMap.put("13:00", "0");
		hashMap.put("13:30", "0");
		hashMap.put("14:00", "0");
		hashMap.put("14:30", "0");
		hashMap.put("15:00", "0");
		hashMap.put("15:30", "0");
		hashMap.put("16:00", "0");
		hashMap.put("16:30", "0");
		hashMap.put("17:00", "0");
		hashMap.put("17:30", "0");
		hashMap.put("18:00", "0");
		hashMap.put("18:30", "0");

		return hashMap;
	}

	private  List<TimeVo> noAvailableMap(LinkedHashMap<String, String> occupyMap) {
		LinkedHashMap<String, String> avHashMap = initHouseKeeperNoAvailable();
		Iterator<Entry<String, String>> iter = occupyMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			String key = entry.getKey();
			avHashMap.put(key, "1");

		}

		List<TimeVo> list = new ArrayList<TimeVo>();

		Iterator<Entry<String, String>> avIterator = avHashMap.entrySet()
				.iterator();
		while (avIterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) avIterator
					.next();
			String key = entry.getKey();
			String value = entry.getValue();
			list.add(new TimeVo(key, value));

		}

		return list;
	}
	private  LinkedHashMap<String, String> initHouseKeeperAvailable() {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

		hashMap.put("8:00", "0");
		hashMap.put("8:30", "0");
		hashMap.put("9:00", "0");
		hashMap.put("9:30", "0");
		hashMap.put("10:00", "0");
		hashMap.put("10:30", "0");
		hashMap.put("11:00", "0");
		hashMap.put("11:30", "0");
		hashMap.put("12:00", "0");
		hashMap.put("12:30", "0");
		hashMap.put("13:00", "0");
		hashMap.put("13:30", "0");
		hashMap.put("14:00", "0");
		hashMap.put("14:30", "0");
		hashMap.put("15:00", "0");
		hashMap.put("15:30", "0");
		hashMap.put("16:00", "0");
		hashMap.put("16:30", "0");
		hashMap.put("17:00", "0");
		hashMap.put("17:30", "0");
		hashMap.put("18:00", "0");
		hashMap.put("18:30", "0");

		return hashMap;
	}
	
	private  List<TimeVo> availableMap(LinkedHashMap<String, String> occupyMap) {
		LinkedHashMap<String, String> avHashMap = initHouseKeeperAvailable();
		Iterator<Entry<String, String>> iter = occupyMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			String key = entry.getKey();
			avHashMap.put(key, "1");

		}

		List<TimeVo> list = new ArrayList<TimeVo>();

		Iterator<Entry<String, String>> avIterator = avHashMap.entrySet()
				.iterator();
		while (avIterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) avIterator
					.next();
			String key = entry.getKey();
			String value = entry.getValue();
			list.add(new TimeVo(key, value));

		}

		return list;
	}
	
	private  void scheduleMap(LinkedHashMap<String, String> hashMap,
			LinkedHashMap<String, String> schedules, Schedule schedule) {
		for (int i = schedule.getServiceTime(); i <= schedule.getUsedEndTime(); i = i + 30) {
			double mimute = i;
			if (mimute < 480 || mimute > 1110) {
				continue;
			}
			String key = String.valueOf(mimute / 60);
			String value=hashMap.get(key);

			schedules.put(value, "1");

		}
	}
	

	@RequestMapping(value = "notice")
	public @ResponseBody
	BaseJsonVo getMessage(int page) {
		if(page==0){
			page=1;
		}
		Long userId = RequestUtils.getUserId();
		List<PushKeeperLogEntity> pushKeeperLogs = keeperPushService.getList(
				userId, PushType.NOTIC.getType(), (page - 1)*20, 20);
		if(pushKeeperLogs!=null&&pushKeeperLogs.size()!=0){
			return BaseJsonVo.success(pushKeeperLogs);
		}
		else {
			return BaseJsonVo.empty();
		}

	}

	@RequestMapping(value = "video")
	public @ResponseBody
	BaseJsonVo getVideo(int page) {
		if(page==0){
			page=1;
		}
		Long userId = RequestUtils.getUserId();
		List<PushKeeperLogEntity> pushKeeperLogs = keeperPushService.getList(
				userId, PushType.VIDEO.getType(),  (page - 1)*20, 20);
		if(pushKeeperLogs!=null&&pushKeeperLogs.size()!=0){
			return BaseJsonVo.success(pushKeeperLogs);
		}
		else {
			return BaseJsonVo.empty();
		}
	}

	@RequestMapping(value = "activity")
	public @ResponseBody
	BaseJsonVo getActivity(int page) {
		if(page==0){
			page=1;
		}
		Long userId = RequestUtils.getUserId();
		List<PushKeeperLogEntity> pushKeeperLogs = keeperPushService.getList(
				userId, PushType.ACTIVTY.getType(), (page - 1)*20, 20);
		if(pushKeeperLogs!=null&&pushKeeperLogs.size()!=0){
			return BaseJsonVo.success(pushKeeperLogs);
		}
		else {
			return BaseJsonVo.empty();
		}
		
	}

}