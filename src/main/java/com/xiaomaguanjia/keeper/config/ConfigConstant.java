package com.xiaomaguanjia.keeper.config;

import java.util.ResourceBundle;

public class ConfigConstant {

    private static final ResourceBundle resource = ResourceBundle.getBundle("config");

    public static String get(String key) {
        return resource.getString(key);
    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(resource.getString(key));
    }

    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(resource.getString(key));
    }
}