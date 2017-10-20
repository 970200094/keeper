package com.xiaomaguanjia.keeper.web.vo;

import java.io.Serializable;

public class KeeperPushVo implements Serializable {
	private String pushUrl;
	
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getPushContent() {
		return pushContent;
	}
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}
	public String getPushIcon() {
		return pushIcon;
	}
	public void setPushIcon(String pushIcon) {
		this.pushIcon = pushIcon;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	private String pushContent;
	private String pushIcon;
	private String pushType;

}
