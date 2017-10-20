package com.xiaomaguanjia.keeper.web.interceptor;

import com.xiaomaguanjia.keeper.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AccessInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(AccessInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String sb = "" +
                "\n-     Access Time: " + new Date() +
                "\n-          Scheme: " + request.getScheme() +
                "\n-    Request Line: " + request.getMethod() + " " + request.getProtocol() +
                "\n-     Request URI: " + request.getRequestURI() +
                "\n-     Remote Host: " + RequestUtils.getIpAddress(request) +
                "\n-   Session isNew: " + request.getSession().isNew() +
                "\n-      Session ID: " + request.getSession().getId() +
                "\n- Request Headers: " + RequestUtils.formatHeaders(request, true) +
                "\n-  Request params: " + RequestUtils.formatParams(request);
        LOG.info(sb);
        return true;
    }
}