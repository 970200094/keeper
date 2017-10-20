package com.xiaomaguanjia.keeper.web.vo;

import com.xiaoma.basicservice.entity.HouseKeeperEntity;

public class KeeperVo {

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

    public KeeperVo(){}

    public KeeperVo(HouseKeeperEntity entity) {
        this.id = entity.getId();
        this.realName = entity.getRealName();
        this.headPath = entity.getHeadPath();
        this.phone = entity.getPhone();
        this.cardNum = entity.getCardNum();
        this.areaId = entity.getAreaId();
        this.hometown = entity.getHometown();
        this.age = entity.getAge();
        this.score = entity.getScore();
        this.isfulltime = entity.getIsfulltime();
        this.keeperType = entity.getLight_keeper();
    }

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
