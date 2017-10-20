package com.xiaomaguanjia.keeper.bean;

import java.util.Date;

public class KeeperCardBean {
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getCardBalance() {
		return cardBalance;
	}
	public void setCardBalance(double cardBalance) {
		this.cardBalance = cardBalance;
	}
	public int getCardRate() {
		return cardRate;
	}
	public void setCardRate(int cardRate) {
		this.cardRate = cardRate;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	private long id;
	
	private double cardBalance;
	private int cardRate;
	private double balance;
	private Date confirmTime;
	private String cardName;
	/**
	 * 人数
	 */
	private int peopleNumber;
	public int getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}  
}
