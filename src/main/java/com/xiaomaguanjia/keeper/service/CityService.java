package com.xiaomaguanjia.keeper.service;

import javax.servlet.http.HttpServletRequest;

import com.xiaomaguanjia.keeper.exception.NetworkException;

public interface CityService {

	String getCityCode(HttpServletRequest request) throws NetworkException;

}