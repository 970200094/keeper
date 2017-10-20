package com.xiaomaguanjia.keeper.enums;

/**
 * 改单申请状态
 * @author sunyan
 *
 */
public enum OrderRejectState {
	
	IN_PROGRESS(1, "进行中"),
	APPROVE(2, "已改单"),
	REFUSE(3, "已驳回");

	int code;
	
	String desc;

	private OrderRejectState(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}
