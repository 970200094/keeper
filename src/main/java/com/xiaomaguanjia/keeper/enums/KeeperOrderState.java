package com.xiaomaguanjia.keeper.enums;

/**
 * 管家订单状态枚举
 * Created by wangfudong on 2015/9/6.
 */
public enum KeeperOrderState {

	NOACTION(0,"待出发"),
	LEAVE(1,"已出发"),
	START(2,"服务中"),
	FINISH(3, "已完成");

	private int value;

	private String name;

	KeeperOrderState(int value, String name) {
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
