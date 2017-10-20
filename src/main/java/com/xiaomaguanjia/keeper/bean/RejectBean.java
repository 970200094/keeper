package com.xiaomaguanjia.keeper.bean;

import java.util.Date;
import java.util.List;

public class RejectBean {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Date getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPeopleSize() {
		return peopleSize;
	}

	public void setPeopleSize(int peopleSize) {
		this.peopleSize = peopleSize;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCommtent() {
		return commtent;
	}

	public void setCommtent(String commtent) {
		this.commtent = commtent;
	}

	private long id;
	private Date serviceTime ;
	private int status;
	private String address;
	private String phone;
	private int peopleSize;
	private int open_state;
	private List<String> userLable;
	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getOpen_state() {
		return open_state;
	}

	public void setOpen_state(int open_state) {
		this.open_state = open_state;
	}

	public List<String> getUserLable() {
		return userLable;
	}

	public void setUserLable(List<String> userLable) {
		this.userLable = userLable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	private String name;
	private String serviceName;
	/**
	 * 其他原因
	 */
	private String explanation;
	/**
	 * 处理理由
	 */
	private String reason;
	/**
	 * 处理结果
	 */
	private String commtent;

}