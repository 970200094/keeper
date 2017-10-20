package com.xiaomaguanjia.keeper.bean;

import java.io.Serializable;

public class BaiduCityBean   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address = "";
	
	private BaiduCityContentBean content;
	
	private String status = "";
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BaiduCityContentBean getContent() {
		return content;
	}

	public void setContent(BaiduCityContentBean content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
