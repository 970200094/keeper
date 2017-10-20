package com.xiaomaguanjia.keeper.bean;

/**
 * Created by wangfudong on 2015/8/14.
 * 管家bean，用于规范命名和接口数据类型一致
 */
public class KeeperBean {

    private Long id;

    private String realName;

    private String headPath;

    private String phone;

    private String cardNum;

    private int areaId;

    private String hometown;

    private int age;

    private int score;

    private int isfulltime;

    private int keeperType;

    public KeeperBean(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIsfulltime() {
        return isfulltime;
    }

    public void setIsfulltime(int isfulltime) {
        this.isfulltime = isfulltime;
    }

    public int getKeeperType() {
        return keeperType;
    }

    public void setKeeperType(int keeperType) {
        this.keeperType = keeperType;
    }
}
