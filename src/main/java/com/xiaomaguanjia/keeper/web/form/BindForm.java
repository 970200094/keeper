package com.xiaomaguanjia.keeper.web.form;

public class BindForm extends BaseForm{

	private String ua;

	private String clientId;
	private String sysversion;


	public String getSysversion() {
		return sysversion;
	}

	public void setSysversion(String sysversion) {
		this.sysversion = sysversion;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "BindForm{" +
				"clientId='" + clientId + '\'' +
				", ua='" + ua + '\'' +
				"} " + super.toString();
	}
}
