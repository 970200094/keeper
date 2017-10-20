package com.xiaomaguanjia.keeper.web.controller;

import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.entity.HouseKeeperEntity;
import com.xiaomaguanjia.keeper.config.ConfigConstant;
import com.xiaomaguanjia.keeper.service.KeeperValidateService;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.form.ValidateForm;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;
import com.xiaomaguanjia.keeper.web.vo.KeeperVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "login")
public class LoginController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HouseKeeperService keeperService;

    @Autowired
    private KeeperValidateService validateService;

    /**
     * 获取登录验证码
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "getcode")
    public @ResponseBody
    BaseJsonVo getCode(@ModelAttribute ValidateForm form) {
        log.info(form.toString());
        if (StringUtils.isEmpty(form.getPhone())) {
            return BaseJsonVo.paramError("请输入正确的手机号");
        }

        try {
            if (form.getUserIp().contains("121.42.")) {
                return BaseJsonVo.paramError("请输入正确的手机号");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HouseKeeperEntity keeper = keeperService.getByPhone(form.getPhone());
        if (keeper == null || keeper.getIsfulltime() == HouseKeeperEntity.STOP_USE) {
            return BaseJsonVo.paramError("请输入正确的手机号");
        }


        validateService.sendCode(form.getPhone());
        return BaseJsonVo.success();
    }

    /**
     * 登录验证
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "validate")
    public @ResponseBody
    BaseJsonVo check(@ModelAttribute ValidateForm form) {
        if (StringUtils.isEmpty(form.getPhone())) {
            return BaseJsonVo.paramError("请输入正确的手机号");
        }
        if (StringUtils.isEmpty(form.getCode())) {
            return BaseJsonVo.paramError("请输入正确验证码");
        }

        UsernamePasswordToken token = new UsernamePasswordToken(form.getPhone(), form.getCode());
        token.setRememberMe(true);
        Subject user = SecurityUtils.getSubject();
        user.login(token);
        final Long userId = RequestUtils.getUserId();
        HouseKeeperEntity keeper = keeperService.get(userId);
        return BaseJsonVo.success(new KeeperVo(keeper));
    }

    @RequestMapping(value = "logout")
      public @ResponseBody BaseJsonVo logOut (){
       RequestUtils.getUserId();

        Subject user = SecurityUtils.getSubject();
        SecurityUtils.getSecurityManager().logout(user);
        return BaseJsonVo.success();
    }

    @RequestMapping(value = "info")
    @ResponseBody
    public BaseJsonVo info(Long keeperId) {
        if (ConfigConstant.getBoolean("system.debug")) {
            if (keeperId == null) {
                keeperId = RequestUtils.getUserId();
            }
            return BaseJsonVo.success(keeperService.get(keeperId));
        }
        return BaseJsonVo.success();
    }
}
