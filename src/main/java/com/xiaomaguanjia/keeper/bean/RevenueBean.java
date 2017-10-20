package com.xiaomaguanjia.keeper.bean;

import java.util.Date;

public class RevenueBean {
	private String serivceName;
	private Date serivceTime;
	private int pirce;
	private int realPrice;
	private int revenPrice;
	private int debitPrice;
	private int realRevenPrice;
	public String getSerivceName() {
		return serivceName;
	}
	public void setSerivceName(String serivceName) {
		this.serivceName = serivceName;
	}
	public Date getSerivceTime() {
		return serivceTime;
	}
	public void setSerivceTime(Date serivceTime) {
		this.serivceTime = serivceTime;
	}
	public int getPirce() {
		return pirce;
	}
	public void setPirce(int pirce) {
		this.pirce = pirce;
	}
	public int getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(int realPrice) {
		this.realPrice = realPrice;
	}
	public int getRevenPrice() {
		return revenPrice;
	}
	public void setRevenPrice(int revenPrice) {
		this.revenPrice = revenPrice;
	}
	public int getDebitPrice() {
		return debitPrice;
	}
	public void setDebitPrice(int debitPrice) {
		this.debitPrice = debitPrice;
	}
	public int getRealRevenPrice() {
		return realRevenPrice;
	}
	public void setRealRevenPrice(int realRevenPrice) {
		this.realRevenPrice = realRevenPrice;
	}

}
