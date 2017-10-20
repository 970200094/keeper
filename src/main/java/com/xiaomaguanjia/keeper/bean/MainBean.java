package com.xiaomaguanjia.keeper.bean;

public class MainBean {
	/**
	 * 视频通知
	 */
	private long video;
	/**
	 * 消息通知
	 */
	private long notic;

	/**
	 * 活动数量
	 */
	private long activity;

	/**
	 * 今日订单
	 */
	private int today;

	private int noComplete; // 未完成

	private int complete;// 完成

	private int service; // 待服务订单;
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVideo() {
		return video;
	}

	public void setVideo(long video) {
		this.video = video;
	}

	public long getNotic() {
		return notic;
	}

	public void setNotic(long notic) {
		this.notic = notic;
	}

	public long getActivity() {
		return activity;
	}

	public void setActivity(long activity) {
		this.activity = activity;
	}

	public int getToday() {
		return today;
	}

	public void setToday(int today) {
		this.today = today;
	}

	public int getNoComplete() {
		return noComplete;
	}

	public void setNoComplete(int noComplete) {
		this.noComplete = noComplete;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}
	
	/**
	 * 管家名称
	 */
	private String keeperName;
	
	/**
	 * 图片
	 */
	private String icon;
	
	/**
	 *  等级描述
	 */
	private String leveDesc;

	public String getKeeperName() {
		return keeperName;
	}

	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLeveDesc() {
		return leveDesc;
	}

	public void setLeveDesc(String leveDesc) {
		this.leveDesc = leveDesc;
	}

}
