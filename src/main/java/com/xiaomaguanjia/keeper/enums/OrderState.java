package com.xiaomaguanjia.keeper.enums;

/**
 * 订单状态枚举
 *
 * Created by wangfudong on 2015/9/2.
 */
public enum OrderState {
	REQUESTED(0, "待确认"),
	CONFIRMED(1, "待服务"),
	FINISHED(2, "已完成"),
	CANCELED(3, "已取消"),
	SERVING(4, "服务中");

	OrderState(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private int value;

	private String name;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
