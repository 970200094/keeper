package com.xiaomaguanjia.keeper.web.vo;

public class TimeVo {
	private String time;
	private String available;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public TimeVo(String time,String available){
		this.time=time;
		this.available=available;
	}

}
