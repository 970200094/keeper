package com.xiaomaguanjia.keeper.web.form;

public class RevenueForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String revenue_month;
	
	private long keeperId;

	public long getKeeperId() {
		return keeperId;
	}

	public void setKeeperId(long keeperId) {
		this.keeperId = keeperId;
	}

	public String getRevenue_month() {
		return revenue_month;
	}

	public void setRevenue_month(String revenue_month) {
		this.revenue_month = revenue_month;
	}


}
