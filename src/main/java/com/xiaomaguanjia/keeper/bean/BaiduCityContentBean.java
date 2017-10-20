package com.xiaomaguanjia.keeper.bean;

import java.io.Serializable;

public class BaiduCityContentBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address = "";
	
	private BaiduCityAddressDetailBean address_detail ;
	
	private BaiduCityPointBean point;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BaiduCityAddressDetailBean getAddress_detail() {
		return address_detail;
	}

	public void setAddress_detail(BaiduCityAddressDetailBean address_detail) {
		this.address_detail = address_detail;
	}

	public BaiduCityPointBean getPoint() {
		return point;
	}

	public void setPoint(BaiduCityPointBean point) {
		this.point = point;
	}
}
