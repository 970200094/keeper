package com.xiaomaguanjia.keeper.service.impl;

import com.xiaoma.basicservice.contract.HouseKeeperService;
import com.xiaoma.basicservice.contract.MessageService;
import com.xiaoma.basicservice.entity.HouseKeeperEntity;
import com.xiaomaguanjia.keeper.config.ConfigConstant;
import com.xiaomaguanjia.keeper.service.BaseService;
import com.xiaomaguanjia.keeper.service.KeeperValidateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户登录验证
 */
@Repository("userValidateService")
public class KeeperValidateServiceImpl extends BaseService implements KeeperValidateService {
    private static final String NAMESPACE = "keeperval";
    private static final Logger logger = LoggerFactory.getLogger(KeeperValidateServiceImpl.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private HouseKeeperService keeperService;

    @Override
    public String getCode(String phone) {
        String key = (NAMESPACE + ":" + phone).trim();
        String codeStr = super.get(key, String.class);
        if (!StringUtils.isEmpty(codeStr)) {
            return codeStr;
        }

        long code = System.currentTimeMillis() % 10000;
        if (code < 1000) {
            code += 1000;
        }

        super.set(key, String.valueOf(code), 60 * 10);
        return String.valueOf(code);
    }

    @Override
    public void sendCode(String phone) {
        messageService.send(0, phone, getMessage(getCode(phone)));
    }

    private static String getMessage(String code) {
        return "验证码：" + code + "。仅用于登录小马管家，请勿将验证码告知他人并确认是您本人操作【小马管家】";
    }

    @Override
    public boolean validate(String phone, String code) {
        if (ConfigConstant.getBoolean("system.debug") && code.equals("0000")) {
            return true;
        }
        String key = (NAMESPACE + ":error_" + phone).trim();
        Integer errorCount = super.get(key, Integer.class);
        if (errorCount != null) {
            errorCount++;
        } else {
            errorCount = 0;
        }
        super.set(key, errorCount, 60 * 30);

        if (errorCount > 50) {
            logger.error("phone:" + phone + "验证次数过多，次数:" + errorCount);
            return false;
        }
        String mCode = getCode(phone);
        if (StringUtils.isEmpty(mCode) || !code.equals(mCode)) {
            return false;
        }
        return true;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public boolean validate(Long keeperId, String phone) {
        String key = (NAMESPACE + ":" + phone + ":" + keeperId).trim();
        Boolean validate = super.get(key, Boolean.class);

        if (validate != null && validate) {
            return validate;
        }

        HouseKeeperEntity keeper = keeperService.get(keeperId, phone);
        if (keeper == null) {
            super.set(key, false, 60 * 60);
            return false;
        } else {
            super.set(key, true, 60 * 60);
            return true;
        }
    }
}