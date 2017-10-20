package com.xiaomaguanjia.keeper.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	/**
     * 空字符串
     */
    public static final String EMPTY = "";
    public static final String NULL_STR = "null";

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	public static String join (Object[] array, String separator){
    	return org.apache.commons.lang.StringUtils.join(array, separator);
    }

    /**
     * 是否为空串(判断是否为null 或 "")
     * isEmpty(null)  = true;
     * isEmpty("")  = true;
     * isEmpty("  ")  = false;
     * @param value 输入的字符串
     * @return 是否为空串
     */
    public static boolean isEmpty(String value)
    {
        return null == value || 0 == value.trim().length();
    }

    /**
     * 是否为空格串(判断是否为null 、"" 或 "   ")
     * isBlank(null)  = true;
     * isBlank("")  = true;
     * isBlank("  ")  = true;
     * @param value 输入的字符串
     * @return 是否为空格串
     */
    public static boolean isBlank(String value)
    {
        //是否为空串，如果为空串直接返回
        return null == value || 0 == value.length() || EMPTY.equals(value.trim()) || NULL_STR.equals(value) || "0000-00-00 00:00:00".equals(value);
    }

    /**
     * 字符串是否为非空
     * @param value 输入的字符串
     * @return 是否为非空格串
     */
    public static boolean isNoBlank(String value)
    {
        return !isBlank(value);
    }

    /**
     * trim字符串操作（如果字符串为空，则返回传入的默认值）
     * @param value 需要处理的字符串
     * @param defValue 默认值字符串
     * @return 处理后的字符串
     */
    public static String defaultIfBlank(String value, String defValue)
    {
        //是否为空格字符串
        if (isBlank(value))
        {
            return defValue;
        }

        //将结果进行trim操作
        return value.trim();
    }

    /**
     * 字符串为空则使用默认字符串
     * @param value 需要处理的字符串
     * @param defValue 默认值字符串
     * @return 处理后的字符串
     */
    public static String defaultIfNull(String value, String defValue)
    {
        //是否为空格字符串
        return null == value ? defValue : value.trim();
    }

    /**
     * 字符串清理
     * @param value 需要处理的字符串
     * @return 清理后的字符串
     */
    public static String emptyIfBlank(String value)
    {
        return isBlank(value) ? EMPTY : value.trim();
    }

    /**
     * 正则匹配字符串
     * @param value 待处理的字符串
     * @param regex 正则表达式
     * @return 是否不匹配
     */
    public static boolean noMatches(String value, String regex)
    {
        return !matches(value, regex);
    }

    /**
     * 正则匹配字符串
     * @param value 待处理的字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean matches(String value, String regex)
    {
        //如果校验字符为空
        if ((null == value) || (null == regex))
        {
            return false;
        }

        //非空的话进行匹配
        return value.matches(regex);
    }

    /**
     * 先对输入字符串进行trim操作再进行正则匹配字符串
     * @param value 待处理的字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean trimAndmatches(String value, String regex)
    {
        //如果校验字符为空
        if ((null == value) || (null == regex))
        {
            return false;
        }

        //非空的话进行匹配
        return value.trim().matches(regex);
    }

    /**
     * 字符串trim并转小写
     * @param value 待处理的字符串
     * @return 转小写后字符串
     */
    public static String trimAndLowerCase(String value)
    {
        return emptyIfBlank(value).toLowerCase(Locale.getDefault());
    }

    /**
     * 字符串trim并转大写
     * @param value 待处理的字符串
     * @return 转大写后字符串
     */
    public static String trimAndUpperCase(String value)
    {
        return emptyIfBlank(value).toUpperCase(Locale.getDefault());
    }

    /**
     * ToString方法
     * @param object 对象
     * @return 描述信息
     */
    public static String trimAndToString(Object object)
    {
        return null == object ? EMPTY : object.toString().trim();
    }

    /**
     * 构建固定长度的字符串
     * @param input 输入字符串
     * @param length 长度
     * @param filledChar 前补充字符
     * @param fillHead 是头填充还是尾填充
     * @return 固定长度后的字符串
     */
    public static String buildFixedLength(String input, int length, char filledChar, boolean fillHead)
    {
        //获取格式化后的字符串
        String inputTrimed = emptyIfBlank(input);

        //判断字符串位数与固定长度数目关系，如果字符串位数大于要求固定长度，则直接返回
        if (inputTrimed.length() >= length)
        {
            return inputTrimed;
        }

        //获取需要拼接的字节数组信息
        int filledLength = length - inputTrimed.length();
        char[] chars = new char[filledLength];
        //使用待填充字符对填充字符数组进行填充
        for (int i = 0; i < filledLength; i++)
        {
            chars[i] = filledChar;
        }

        //根据头填充还是尾填充返回结果
        return fillHead ? new String(chars) + inputTrimed : inputTrimed + new String(chars);
    }
   
    /**
     * 构建固定长度的字符串
     * @param sequenceValue 序列值
     * @param pattern 字符串格式
     * @return 固定长度的序列字符串
     */
    public static String buildFixedLength(long sequenceValue, String pattern)
    {
        //获取格式化的格式字符串信息
        String newPattern = StringUtil.emptyIfBlank(pattern);
       
        //使用字符串格式化进行格式化
        DecimalFormat decimalFormat = new DecimalFormat(newPattern);
       
        //返回格式化字符串
        return decimalFormat.format(sequenceValue);
    }
   
    /**
     * 判断是否为中文字符
     * @param charValue 字符值
     * @return 是否为中文字符
     */
    public static boolean isChineseChar(char charValue)
    {
        //获取字符对象
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(charValue);
       
        //返回中文字符
        return unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || unicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
   
    /**
     * 获取字符串的字节长度
     * @param value 字符串
     * @return 字符串的字节长度：一个汉字的字节长度为2
     */
    public static int getStringByteLength(String value)
    {
        //判断取值是否为空
        return null == value ? 0 : value.getBytes().length;
    }
   
    /**
     * 字符串剔除特殊字符
     * @param input 输入字符串
     * @param rejectChar 剔除字符
     * @return 剔除后的字符串
     */
    public static String rejectHeadSpecialChar(String input, char rejectChar)
    {
        //判断输入字符串是否为空，为空则直接返回空
        if (StringUtil.isBlank(input))
        {
            return StringUtil.EMPTY;
        }
       
        //获取前后trim的字符串
        String inputTrim = input.trim();
       
        //获取输入字符串长度信息
        int inputTrimLength = inputTrim.length();
       
        //初始化特殊字符串标记位
        int index = 0;
       
        //循环检查字符
        for (; index < inputTrimLength; index++)
        {
            //判断是否不等于特殊字符串，如果是则推出
            if (rejectChar != inputTrim.charAt(index))
            {
                break;
            }
        }
       
        //截取配置固定长度信息
        return inputTrim.substring(index, inputTrimLength);
    }
    /**
     * 判断是否为数字
     * @param str
     * @return boolean
     */
    public static boolean isNumeric(String input){
    	String str = input.replace(".", "");
		for (int i = str.length();--i>=0;){   
			if (!Character.isDigit(str.charAt(i))){
					return false;
			}
		}
		return true;
    }
    /**
     * 判断是否为数字
     * @param str
     * @return boolean
     */
    public static boolean isNumericByTrim(String input){
    	String str = input.replace(".", "").replace(" ", "");
		for (int i = str.length();--i>=0;){   
			if (!Character.isDigit(str.charAt(i))){
					return false;
			}
		}
		return true;
    }
    
    /**
     * 把枚举的map转换成下拉列表
     * @param map
     * @return
     */
    public static List<Map<String,String>> mapToList(Map<Integer,String> map){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> new_map=null;
		if(map!=null&&map.size()>0){
			for(Integer key:map.keySet()){
				new_map=new HashMap<String, String>();
				new_map.put("name", map.get(key));
				new_map.put("value", key+"");
				list.add(new_map);
			}
		} 
		return list;
	}
    
    /**
	 * 提供精确的乘法运算。
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static Double mul(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.multiply(b2).doubleValue();
	}
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param value1
	 *            被加数
	 * @param value2
	 *            加数
	 * @return 两个参数的和
	 */
	public static Double add(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.subtract(b2).doubleValue();
	}
	
	private static final Integer DEF_DIV_SCALE = 2;
	
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public static Double div(Double dividend, Double divisor) {
		return div(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static Double div(Double dividend, Double divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
     * 获取UUID（去除-）
     * @return String
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
    
    public static String replacePhone(String phone){ 
    	return phone.substring(0,3)+"****"+phone.substring(7);
    }
    
    public static double ifDoubleNUll(Double d,double default_value){
    	if(d==null){
    		return default_value;
    	}
    	return d;
    }
    public static void main(String[] s){
    	//System.out.println(replacePhone("18610611360"));
    }
}
