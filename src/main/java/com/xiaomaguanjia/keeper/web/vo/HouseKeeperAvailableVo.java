package com.xiaomaguanjia.keeper.web.vo;

import java.util.List;

public class HouseKeeperAvailableVo {

	private  String data;
	private List<TimeVo> timesVo;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<TimeVo> getTimesVo() {
		return timesVo;
	}
	public void setTimesVo(List<TimeVo> timesVo) {
		this.timesVo = timesVo;
	}
}
