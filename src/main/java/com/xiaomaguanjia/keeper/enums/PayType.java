package com.xiaomaguanjia.keeper.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付方式
 * Created by wangfudong on 2015/9/2.
 */
public enum PayType {

    UNKNOWN(0, "未知"),
    ALIPAY_ONLINE(1, "支付宝支付"),
    WECHAT_ONLINE(2, "微信支付"),
    BALANCE(3, "余额支付"),
    ALIPAY_AND_BALANCE(4,"余额+支付宝支付"),
    WECHAT_AND_BALANCE(5, "余额+微信支付"),
    CASH(6, "现金支付"),
    ALIPAY_TRANSFER(7, "支付宝转账"),
    POS(9,"POS机支付"),
    BALANCE_CASH(10, "余额+现金支付"),
    BALANCE_ALIPAYTRANSFER(11, "余额+支付宝转账"),
    FREE(12, "免费支付"),
    AFTERSERVICEPAY(13, "服务后支付"),
    WECHAT_JS_PAY(14, "微信公众账号支付"),
    WECHATJSPAY_AND_BALANCE(15, "余额+微信公众账号支付"),
 	THIRD_PAY(16, "第三方结算");


    private int status;
    private String desc;

    PayType(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public PayType getCombinedPayType() {
        if (this == PayType.ALIPAY_ONLINE) {
            return PayType.ALIPAY_AND_BALANCE;
        } else if (this == PayType.WECHAT_ONLINE) {
            return PayType.WECHAT_AND_BALANCE;
        } else if(this == PayType.WECHAT_JS_PAY){
            return PayType.WECHATJSPAY_AND_BALANCE;
        }
        return null;
    }

    public static Map<Integer,String> payTypeName;

    public static Map<Integer,String> cmsPayTypeName;

    static{
        payTypeName= new HashMap<Integer,String>();
        payTypeName.put(ALIPAY_ONLINE.status, ALIPAY_ONLINE.desc);
        payTypeName.put(WECHAT_ONLINE.status, WECHAT_ONLINE.desc);
        payTypeName.put(BALANCE.status, BALANCE.desc);
        payTypeName.put(ALIPAY_AND_BALANCE.status, ALIPAY_AND_BALANCE.desc);
        payTypeName.put(WECHAT_AND_BALANCE.status, WECHAT_AND_BALANCE.desc);
        payTypeName.put(CASH.status, CASH.desc);
        payTypeName.put(ALIPAY_TRANSFER.status, ALIPAY_TRANSFER.desc);
        payTypeName.put(POS.status, POS.desc);
        payTypeName.put(BALANCE_CASH.status, BALANCE_CASH.desc);
        payTypeName.put(BALANCE_ALIPAYTRANSFER.status, BALANCE_ALIPAYTRANSFER.desc);
        payTypeName.put(FREE.status, FREE.desc);
        payTypeName.put(WECHAT_JS_PAY.status, WECHAT_JS_PAY.desc);
        payTypeName.put(WECHATJSPAY_AND_BALANCE.status, WECHATJSPAY_AND_BALANCE.desc);

        cmsPayTypeName= new HashMap<Integer,String>();
        cmsPayTypeName.put(BALANCE.status, BALANCE.desc);
        cmsPayTypeName.put(CASH.status, CASH.desc);
        cmsPayTypeName.put(POS.status, POS.desc);
        cmsPayTypeName.put(BALANCE_CASH.status, BALANCE_CASH.desc);
        cmsPayTypeName.put(FREE.status, FREE.desc);
    }

    public static  String getName(Integer key){
        return payTypeName.get(key);
    }

    /**
     * 判断是否允许线上退款
     * @param modeOfPayment
     * @return
     */
    public static boolean isAllowRefund(int modeOfPayment){
        return modeOfPayment == PayType.ALIPAY_AND_BALANCE.getStatus()
                || modeOfPayment == PayType.ALIPAY_ONLINE.getStatus() || modeOfPayment == PayType.BALANCE.getStatus()
                || modeOfPayment == PayType.WECHAT_AND_BALANCE.getStatus()
                || modeOfPayment == PayType.WECHAT_JS_PAY.getStatus() || modeOfPayment == PayType.WECHAT_ONLINE.getStatus()
                || modeOfPayment == PayType.WECHATJSPAY_AND_BALANCE.getStatus()||modeOfPayment == PayType.BALANCE_CASH.getStatus();
    }
}
