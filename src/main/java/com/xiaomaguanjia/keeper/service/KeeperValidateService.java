package com.xiaomaguanjia.keeper.service;

public interface KeeperValidateService {

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @return
     */
    String getCode(String phone);

    /**
     * 发送登录验证码
     *
     * @param phone 手机号
     * @return
     */
    void sendCode(String phone);

    /**
     * 登录验证
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    boolean validate(String phone, String code);

    /**
     * 验证用户id和手机号是否匹配
     *
     * @param keepId 用户id
     * @param phone  手机号
     * @return
     */
    boolean validate(Long keepId, String phone);
}