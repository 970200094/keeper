package com.xiaomaguanjia.keeper.web.vo;

import java.util.List;

public class HouseKeeperScheduleVo {

	public long getDate() {
		return date;
	}
	@Override
	public String toString() {
		return "HouseKeeperPlanTimeVo [date=" + date + ", schedules="
				+ schedules + "]";
	}
	public void setDate(long date) {
		this.date = date;
	}
	/**
	 *  代表当前时间
	 */
	private long date;
	
	public List<Schedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	private List<Schedule>schedules;
}
