package com.xiaomaguanjia.keeper.web.vo;

/**
 * 基础返回实体
 * 
 * @author 王昌龙
 * 
 */
public class BaseJsonVo {
	public static final int SUCCESS_CODE = 100;
	public static final String SUCCESS_MESSAGE = "操作成功";
	public static final int ERROR_CODE = 101;
	public static final String ERROR_MESSAGE = "未知错误";
	public static final int PARAM_CODE = 102;
	public static final String PARAM_MESSAGE = "参数错误";
	public static final int UPDATE_CODE = 103;
	public static final String UPDATE_MESSAGE = "有新版本，请更新！";
	public static final int EXPIRED_CODE = 104;
	public static final String EXPIRED_MESSAGE = "无登陆或登录过期";
	public static final int NO_LOGIN_CODE = 105;
	public static final String NO_LOGIN_MESSAGE = "管家端未登录";
	public static final int NULL_CODE = 110;
	public static final String NULL_MESSAGE = "无数据";
	public static final int NOT_MODIFIED_CODE = 304;
	public static final String NOT_MODIFIED_MESSAGE = "使用缓存";
	/** 支付相关CODE **/
	public static final int PAY_CHECK_PASS_CODE = 200;//支付CHECK OK
	public static final int PAY_FRONT_CHECK_ERROR_CODE = 201;//支付CHECK ERR

	private int code;
	private String message;
	private long version;
    private long serverTime;
	private Object value;

    public static BaseJsonVo success(){
        return new BaseJsonVo(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

	public static BaseJsonVo success(Object value) {
		return new BaseJsonVo(SUCCESS_CODE, SUCCESS_MESSAGE, value);
	}
	public static BaseJsonVo success(Object value,long serverTime) {
		return new BaseJsonVo(SUCCESS_CODE, SUCCESS_MESSAGE, serverTime, value);
	}


	public static BaseJsonVo success(long version, Object value) {
		return new BaseJsonVo(SUCCESS_CODE, SUCCESS_MESSAGE, version, value);
	}

	public static BaseJsonVo error() {
		return new BaseJsonVo(ERROR_CODE, ERROR_MESSAGE);
	}

	public static BaseJsonVo empty() {
		return new BaseJsonVo(NULL_CODE, NULL_MESSAGE);
	}
	
	public static BaseJsonVo notModified() {
		return new BaseJsonVo(NOT_MODIFIED_CODE, NOT_MODIFIED_MESSAGE);
	}

	public static BaseJsonVo paramError(String message) {
		return new BaseJsonVo(PARAM_CODE, message);
	}
	
	public static BaseJsonVo payFrontCheckError(String message) {
		return new BaseJsonVo(PAY_FRONT_CHECK_ERROR_CODE, message);
	}

	public BaseJsonVo() {
		super();
	}

	public BaseJsonVo(int code, String message) {
		super();
		this.message = message;
		this.code = code;
        this.serverTime = System.currentTimeMillis();
	}
	public BaseJsonVo (int code ,String message,long serverTime ,Object value){
		super();
		this.message = message;
		this.code = code;
        this.serverTime = serverTime;
        this.value = value;
	}

	public BaseJsonVo(int code, String message, Object value) {
		super();
		this.code = code;
		this.message = message;
        this.serverTime = System.currentTimeMillis();
		this.value = value;
	}

//	public BaseJsonVo(int code, String message, long version, Object value) {
//		super();
//		this.code = code;
//		this.message = message;
//		this.version = version;
//        this.serverTime = System.currentTimeMillis();
//		this.value = value;
//	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "BaseJsonVo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", version=" + version +
                ", serverTime=" + serverTime +
                ", value=" + value +
                '}';
    }
}
