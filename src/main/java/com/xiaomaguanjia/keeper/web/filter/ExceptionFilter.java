package com.xiaomaguanjia.keeper.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.entity.HouseKeeperEntity;
import com.xiaoma.p4jtools.json.JsonUtils;
import com.xiaoma.p4jtools.type.PathType;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

public class ExceptionFilter implements Filter {

	private static final Logger LOG = LoggerFactory
			.getLogger(ExceptionFilter.class);

	private HouseKeeperService houseKeeperService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest re = (HttpServletRequest) request;
			if (!PathType.isStaticFiles(re.getServletPath())) {
				if (houseKeeperService == null) {
					ApplicationContext application = (ApplicationContext) re
							.getSession()
							.getServletContext()
							.getAttribute(
									WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
					houseKeeperService = application.getBean(
							"houseKeeperService", HouseKeeperService.class);
				}
				LOG.error("re.getServletPath()=="+re.getServletPath());
					Long keeperId = RequestUtils.getKeeperId();
					if(keeperId!=null){
						final HouseKeeperEntity keeperEntity = houseKeeperService
								.get(keeperId);

						if (keeperEntity == null) {
							BaseJsonVo result = new BaseJsonVo(
									BaseJsonVo.NO_LOGIN_CODE,
									BaseJsonVo.NO_LOGIN_MESSAGE);

							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().print(JsonUtils.toJson(result));
							RequestUtils.loginOut();
							LOG.error("keeperEntity == nullkeeperEntity == null");
							return;
						}
						if (keeperEntity.getStop_date() != null
								&& keeperEntity.getStop_date().getTime() < new Date()
										.getTime()) {
							BaseJsonVo result = new BaseJsonVo(
									BaseJsonVo.NO_LOGIN_CODE,
									BaseJsonVo.NO_LOGIN_MESSAGE);

							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().print(JsonUtils.toJson(result));
							RequestUtils.loginOut();
							LOG.error("getStop_dategetStop_date");
							return;
						} else if (keeperEntity.getIsfulltime() != 1) {
							BaseJsonVo result = new BaseJsonVo(
									BaseJsonVo.NO_LOGIN_CODE,
									BaseJsonVo.NO_LOGIN_MESSAGE);

							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().print(JsonUtils.toJson(result));
							RequestUtils.loginOut();
							LOG.error("getIsfulltimegetIsfulltime");
							
							return;
						}
					}
					
				}


			chain.doFilter(request, response);
		} catch (Exception ex) {
			
			try {
				BaseJsonVo result = null;
				if (ex.getCause().getMessage().equals(BaseJsonVo.NO_LOGIN_MESSAGE)) {
					result = new BaseJsonVo(BaseJsonVo.NO_LOGIN_CODE,
							BaseJsonVo.NO_LOGIN_MESSAGE);
					LOG.error("sssssss");
					RequestUtils.loginOut();
				} else {
					result = new BaseJsonVo(BaseJsonVo.ERROR_CODE, ex.getCause()
							.getMessage());
				}
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().print(JsonUtils.toJson(result));
			} catch (Exception e) 
			{
		
//				BaseJsonVo result = new BaseJsonVo(BaseJsonVo.NO_LOGIN_CODE,
//						BaseJsonVo.NO_LOGIN_MESSAGE);
//				response.setContentType("application/json;charset=UTF-8");
//				response.getWriter().print(JsonUtils.toJson(result));
//				
//				RequestUtils.loginOut();
				LOG.error("ex.getCause().getMessage()"+ex.getCause().getMessage());
			}
		}

	}

	@Override
	public void destroy() {

	}
}