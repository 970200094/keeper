package com.xiaomaguanjia.keeper.aspet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaoma.basicservice.contract.keeper.KeeperLogService;
import com.xiaoma.basicservice.entity.keeper.KeeperLogEntity;
import com.xiaomaguanjia.keeper.util.RequestUtils;

@Component
@Aspect
public class LogAspet {

	@Autowired
	private KeeperLogService keeperLogService;

	@Pointcut("execution(* com.xiaomaguanjia.keeper.web.controller.*.*(..))")
	private void anyMethod() {
	}

	@After("anyMethod()")
	public void saveKeeperLog() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		Long keeperId=RequestUtils.getKeeperId();
		if(keeperId!=null){
			KeeperLogEntity entity=new KeeperLogEntity();
			entity.setKeeperid(keeperId);
			entity.setUrl(request.getRequestURI());
			
			entity.setIp(RequestUtils.getIpAddress(request));
			
			
			StringBuilder builder = new StringBuilder();
			@SuppressWarnings("unchecked")
			Map<String, String[]> map = request.getParameterMap();
			if (map != null) {
                for(Map.Entry<String, String[]> entry : map.entrySet()){
                    String key = entry.getKey();
                    String value = "";
                    if(entry.getValue() != null && entry.getValue().length > 0){
                        value = entry.getValue()[0];
                    }
                    if ("version".equals(key)) {
                    	entity.setVersion(value);
                    } else if ("platform".equals(key) && StringUtils.isNotEmpty(value)) {
                    	entity.setPlatform(Integer.valueOf(value));
                    } else if ("imei".equals(key)) {
                    	entity.setImei(value);
                    } else if ("uuid".equals(key)) {
                    	entity.setUuid(value);
                    } else if ("token".equals(key)) {
                    	entity.setToken(value);
                    } else if ("apn".equals(key)) {
                    	entity.setApn(value);
                    } else if ("ssid".equals(key)) {
                    	entity.setSsid(value);
                    } else if ("lat".equals(key) && StringUtils.isNotEmpty(value)) {
                    	entity.setLat(Double.valueOf(value));
                    } else if ("lng".equals(key) && StringUtils.isNotEmpty(value)) {
                    	entity.setLng(Double.valueOf(value));
                    } else if ("phone".equals(key)) {
                    	entity.setPhone(value);
                    } else if ("keeperId".equals(key) && StringUtils.isNotEmpty(value)) {
//                    	entity.setKeeperid(Long.valueOf(value));
                    } else {
                        builder.append("&").append(key).append("=").append(value);
                    }
                }
			}
			entity.setParams(builder.toString());
			keeperLogService.save(entity);

		}

		
		
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "*");
//		response.setHeader("Access-Control-Allow-Credentials", "true");


	}
	
	
}
