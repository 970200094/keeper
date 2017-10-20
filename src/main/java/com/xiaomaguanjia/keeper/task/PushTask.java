package com.xiaomaguanjia.keeper.task;

import java.util.ArrayList;
import java.util.List;

import com.xiaomaguanjia.message.contract.service.KeeperPushService;
import com.xiaomaguanjia.message.contract.vo.ClientPushVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.contract.push.PushKeeperContentSerivce;
import com.xiaoma.basicservice.entity.HouseKeeperEntity;
import com.xiaoma.basicservice.entity.push.PushKeeperContentEntity;
import com.xiaomaguanjia.keeper.web.vo.KeeperPushVo;

@Component("PushTask")
public class PushTask {
    private static final Logger log = LoggerFactory.getLogger(PushTask.class);
	@Autowired
	private KeeperPushService keeperPushService;
	@Autowired
	private HouseKeeperService houseKeeperService;
	@Autowired
	private PushKeeperContentSerivce pushKeeperContentSerivce;
	
	
	private void pushMessage(){
		List<PushKeeperContentEntity> list = pushKeeperContentSerivce
				.getAll(null);
		if (list != null && list.size() != 0) {
			for (PushKeeperContentEntity entity : list) {
				String json=entity.getContent();
				Gson gson=new Gson();
				ClientPushVo<KeeperPushVo> pushVo= gson.fromJson(json, ClientPushVo.class);
				entity.setStatus(0);
				List<Long> KeeperIds=new ArrayList<Long>();
				List<HouseKeeperEntity>houseKeeperEntities =houseKeeperService.getNotStopUsedKeepers(0,0);
			    for (HouseKeeperEntity houseKeeperEntity : houseKeeperEntities) {
			    	KeeperIds.add(houseKeeperEntity.getId());
				}
				keeperPushService.pushKeeper(KeeperIds, pushVo, entity.getId());
				pushKeeperContentSerivce.update(entity);

			}
		
		}
	}
	@Scheduled(cron = "0 0 */3 * * ?")
	public void sendPush(){
//		pushMessage();
	}
}
