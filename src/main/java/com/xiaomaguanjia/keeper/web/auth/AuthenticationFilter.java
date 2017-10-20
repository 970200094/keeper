package com.xiaomaguanjia.keeper.web.auth;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && subject.isRemembered()) {
            Session session = subject.getSession(true);
            if (session.getAttribute("userId") == null) {
                session.setAttribute("userId", subject.getPrincipal());
            }
        }
        return subject.isAuthenticated() || subject.isRemembered();
    }
}