package com.xiaomaguanjia.keeper.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.contract.keeper.KeeperLogService;
import com.xiaoma.basicservice.entity.keeper.KeeperLogEntity;
import com.xiaoma.p4jtools.http.IpUtil;
import com.xiaoma.p4jtools.type.PathType;
import com.xiaomaguanjia.keeper.ThreadPool;

/**
 * 初始化一些参数资源等。<br/>
 * 提供了设置请求编码格式方法：需在context-param中设置“request.charsetencoding”，
 * 并配置本filter为filter链第一个。
 * 
 * @author partner4java
 * 
 */
public class InitFilter implements Filter {
	private static Log logger = LogFactory.getLog(InitFilter.class);

	private KeeperLogService keeperLogService;
	


	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		doSetCharsetencoding(req);
		
		try {
			if (!PathType.isStaticFiles(request.getServletPath())) {
				// 初始化requestLogService
				if (keeperLogService == null) {
					ApplicationContext application = (ApplicationContext) request.getSession().getServletContext()
							.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
					keeperLogService = application.getBean("keeperLogService", KeeperLogService.class);
				}

				// 构造RequestLogEntity对象
//				final KeeperLogEntity requestLog = new KeeperLogEntity();
//				requestLog.setUrl(request.getRequestURI());
//                requestLog.setIp(IpUtil.getRemoteAddrIp(request));
//
//				StringBuilder builder = new StringBuilder();
//				@SuppressWarnings("unchecked")
//				Map<String, String[]> map = request.getParameterMap();
//				if (map != null) {
//                    for(Map.Entry<String, String[]> entry : map.entrySet()){
//                        String key = entry.getKey();
//                        String value = "";
//                        if(entry.getValue() != null && entry.getValue().length > 0){
//                            value = entry.getValue()[0];
//                        }
//                        if ("version".equals(key)) {
//                            requestLog.setVersion(value);
//                        } else if ("platform".equals(key) && StringUtils.isNotEmpty(value)) {
//                            requestLog.setPlatform(Integer.valueOf(value));
//                        } else if ("imei".equals(key)) {
//                            requestLog.setImei(value);
//                        } else if ("uuid".equals(key)) {
//                            requestLog.setUuid(value);
//                        } else if ("token".equals(key)) {
//                            requestLog.setToken(value);
//                        } else if ("apn".equals(key)) {
//                            requestLog.setApn(value);
//                        } else if ("ssid".equals(key)) {
//                            requestLog.setSsid(value);
//                        } else if ("lat".equals(key) && StringUtils.isNotEmpty(value)) {
//                            requestLog.setLat(Double.valueOf(value));
//                        } else if ("lng".equals(key) && StringUtils.isNotEmpty(value)) {
//                            requestLog.setLng(Double.valueOf(value));
//                        } else if ("phone".equals(key)) {
//                            requestLog.setPhone(value);
//                        } else if ("keeperId".equals(key) && StringUtils.isNotEmpty(value)) {
//                            requestLog.setKeeperid(Long.valueOf(value));
//                        } else {
//                            builder.append("&").append(key).append("=").append(value);
//                        }
//                    }
//				}
//				requestLog.setParams(builder.toString());
//                //输出api版本号
//                String apiVersion = request.getHeader("version");
//                if(StringUtils.isEmpty(apiVersion)){
//                    apiVersion = "v1.0";
//                }
//				logger.info("请求参数：apiVersion=" + apiVersion + " , " + requestLog);
//
//				ThreadPool.services.execute(new Runnable() {
//
//					@Override
//					public void run() {
//						try {
//							keeperLogService.save(requestLog);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		chain.doFilter(req, rep);
	}

	/**
	 * 设置请求编码格式，一定要设置为filter链第一个
	 * 
	 * @param req
	 * @throws UnsupportedEncodingException
	 */
	private void doSetCharsetencoding(ServletRequest req) throws UnsupportedEncodingException {
		String charsetencoding = ((HttpServletRequest) req).getSession().getServletContext().getInitParameter("request.charsetencoding");
		if (logger.isDebugEnabled()) {
			logger.debug("charsetencoding:" + charsetencoding);
		}
		if (charsetencoding != null && !"".equals(charsetencoding.trim())) {
			req.setCharacterEncoding(charsetencoding);
		} else {
			req.setCharacterEncoding("UTF-8");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
