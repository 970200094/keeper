package com.xiaomaguanjia.keeper.web.form;

import com.xiaoma.p4jtools.http.IpUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class BaseForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 将之前版本号规则修改为3位：1.10 */
    private String version;

    /** Ios：1；andro：2；weixin：3；m：4 */
    private int platform;

    /** android设备提供 */
    private String imei;

    /** 维度 */
    private double lat;

    /** ios唯一标识 */
    private String uuid;

    /** ios用 */
    private String token;

    /** 接入网络类型：wifi、… */
    private String apn;

    private String ssid;

    /** 经度 */
    private double lng;

    /** 当前登录手机号（登录） */
    private String phone;

//    /** 管家id（登录） */
//    private long keeperId;

    /**
     * 获取当前用户请求ip
     *
     * @return
     */
    public String getUserIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return IpUtil.getRemoteAddrIp(request);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	@Override
	public String toString() {
		return "BaseForm [version=" + version + ", platform=" + platform
				+ ", imei=" + imei + ", lat=" + lat + ", uuid=" + uuid
				+ ", token=" + token + ", apn=" + apn + ", ssid=" + ssid
				+ ", lng=" + lng + ", phone=" + phone + "]";
	}

}