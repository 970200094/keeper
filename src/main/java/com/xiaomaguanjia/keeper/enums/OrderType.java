package com.xiaomaguanjia.keeper.enums;

/**
 * 订单类型枚举
 * Created by wangfudong on 2015/9/9.
 */
public enum OrderType {
	NORMAL(0, "普通订单"),
	LIGHT(1, "轻管家订单");

	private int value;

	private String name;

	OrderType(int value, String name) {
		this.value = value;
		this.name = name;
	}

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
