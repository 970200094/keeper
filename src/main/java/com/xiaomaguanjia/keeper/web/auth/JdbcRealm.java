package com.xiaomaguanjia.keeper.web.auth;

import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.entity.HouseKeeperEntity;
import com.xiaomaguanjia.keeper.service.KeeperValidateService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class JdbcRealm extends AuthorizingRealm {

    @Autowired
    private KeeperValidateService keeperValidateService;

    @Autowired
    private HouseKeeperService houseKeeperService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String name = upt.getUsername();
        String password = new String(upt.getPassword());
        if (!keeperValidateService.validate(name, password)) {
            throw new AuthenticationException("请输入正确验证码");
        }
        final HouseKeeperEntity keeperEntity = houseKeeperService.getByPhone(name);
        if (keeperEntity == null) {
            throw new AuthenticationException("请输入正确手机号");
        }
        if (keeperEntity.getStop_date() != null && keeperEntity.getStop_date().getTime() < new Date().getTime()) {
            throw new AuthenticationException("账号已停用");
        } else if (keeperEntity.getIsfulltime() != 1) {
            throw new AuthenticationException("账号已停用");
        }
        return new SimpleAuthenticationInfo(keeperEntity.getId(), password, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }
}
