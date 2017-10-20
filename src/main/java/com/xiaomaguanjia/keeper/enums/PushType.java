package com.xiaomaguanjia.keeper.enums;

public enum PushType {

	VIDEO(3, "视频"), NOTIC(1, "消息通知"), ACTIVTY(2, "活动");
	private int type;
	private String desc;

	PushType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
