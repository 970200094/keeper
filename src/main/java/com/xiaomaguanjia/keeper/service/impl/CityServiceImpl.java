package com.xiaomaguanjia.keeper.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xiaoma.p4jtools.Config;
import com.xiaoma.p4jtools.http.IpUtil;
import com.xiaoma.p4jtools.http.UrlGetContent;
import com.xiaomaguanjia.keeper.bean.BaiduCityBean;
import com.xiaomaguanjia.keeper.exception.NetworkException;
import com.xiaomaguanjia.keeper.service.CityService;
@Service
public class CityServiceImpl implements CityService {

	@Override
	public String getCityCode(HttpServletRequest request)
			throws NetworkException {

		String url = Config.value("http://api.map.baidu.com/location/ip");
		Map<String, Object> params = new HashMap<String, Object>();
		String ip = IpUtil.getRemoteAddrIp(request);
		params.put("ip", ip);
		params.put("ak", "3b02285209b71ecdcfcd7bfa52b152b3");
		params.put("coor", "bd09ll");
		String content = UrlGetContent.getContent(url
				+ "order/houserkeeper/details", params, "utf-8");

		String result = "";
		if (StringUtils.isNotEmpty(content)) {
			Gson gson = new Gson();
			BaiduCityBean aBaseBean = gson.fromJson(content,
					BaiduCityBean.class);
			if (aBaseBean != null && "0".equals(aBaseBean.getStatus())) {
				result = aBaseBean.getContent().getAddress_detail()
						.getCity_code();
			}
		}
		return result;

	}

}