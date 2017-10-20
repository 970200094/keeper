package com.xiaomaguanjia.keeper.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xiaoma.util.StringUtils;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

public class RequestUtils {

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String formatParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            sb.append("\n\t").append(entry.getKey()).append(": ");
            String[] valueList = entry.getValue();
            if (valueList != null && valueList.length > 0)
                if (valueList.length == 1) {
                    sb.append(valueList[0].replace("\n", ""));
                } else {
                    sb.append("[ ");
                    for (String s : valueList) {
                        sb.append(s).append(" | ");
                    }
                    int deliIndex = sb.length() - 2;
                    if (sb.length() > 1 || sb.charAt(deliIndex) == '|') {
                        sb.deleteCharAt(deliIndex);
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("]");
                }
        }
        return sb.toString();
    }

    public static String formatHeaders(HttpServletRequest request, boolean ignoreCookie) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> names = request.getHeaderNames();
        if (names == null) {
            return "";
        }
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.toLowerCase().equals("cookie") && ignoreCookie) {
                continue;
            }
            sb.append(String.format("\n\t%s: %s", name, request.getHeader(name)));
        }
        return sb.toString();
    }

    public static String formatSession(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        HttpSession session = request.getSession();
        if (session != null) {
            Enumeration<String> names = session.getAttributeNames();
            if (names != null) {
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    sb.append(String.format("\n\t%s: %s", name, session.getAttribute(name)));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 完整的URL
     * 如：http://m.xiaomaguanjia.com/h5/order/info?orderId=123
     */
    public static String getFullUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            url = String.format("%s?%s", url, request.getQueryString());
        }
        return url;
    }

    /**
     * 域名后面的部分
     * 如：/h5/order/info?orderId=123
     */
    public static String getFullUri(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            url = url + "?" + request.getQueryString();
        }
        return url;
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            map.put(headerName, request.getHeader(headerName));
        }
        return map;
    }

    public static Map<String, Cookie> getCookies(HttpServletRequest request) {
        Map<String, Cookie> map = new HashMap<>();
        for (Cookie cookie : request.getCookies()) {
            map.put(cookie.getName(), cookie);
        }
        return map;
    }

    public static Map<String, String> getParameter(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            map.put(parameterName, request.getParameter(parameterName));
        }
        return map;
    }

    public static Long getUserId()  {
        Subject subject = SecurityUtils.getSubject();
        if (null != subject && null != subject.getPrincipal()) {
            return Long.valueOf(subject.getPrincipal().toString());
        }
        throw new RuntimeException(BaseJsonVo.NO_LOGIN_MESSAGE);
    }
    
    public static Long getKeeperId(){
    	Subject subject = SecurityUtils.getSubject();
        if (null != subject && null != subject.getPrincipal()) {
            return Long.valueOf(subject.getPrincipal().toString());
        }
        return null;
    }
    
    public static void loginOut(){
    	Subject user = SecurityUtils.getSubject();
    	if(user!=null){
            SecurityUtils.getSecurityManager().logout(user);

    	}
    }
    
}