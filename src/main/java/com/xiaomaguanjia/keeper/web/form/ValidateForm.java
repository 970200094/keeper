package com.xiaomaguanjia.keeper.web.form;

/**
 * 用户验证码服务
 * 
 * @author 王昌龙
 * 
 */
public class ValidateForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	/**
	 * 验证码
	 */
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    @Override
    public String toString() {
        return "KeeperValidateForm{" + super.toString() +
                ", code='" + code + '\'' +
                '}';
    }
}
