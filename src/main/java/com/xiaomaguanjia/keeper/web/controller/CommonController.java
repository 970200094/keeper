package com.xiaomaguanjia.keeper.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoma.p4jtools.Config;
import com.xiaomaguanjia.keeper.util.UrlGetContent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "common")
public class CommonController {
    @RequestMapping(value = "tools")

    public @ResponseBody void getTools(HttpServletRequest request, HttpServletResponse httpServletResponse , @RequestParam(required=false) HashMap<String, Object> map, @RequestParam String host){
    	String apiUrl= Config.value("api.url")+host;

		StringBuffer sb=null;

		Cookie [] cookies=request.getCookies();
		if(cookies!=null){
			sb=new StringBuffer();
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie=cookies[i];
				sb.append(cookie.getName());
				sb.append("=");
				sb.append(cookie.getValue());
				sb.append(";");



			}

		}





		HashMap<String,Object> params=new HashMap<String, Object>();
		if(map!=null&&map.size()!=0){
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key=entry.getKey();
				if(key.equals("host")){

				}
//				else if(key.equals("cookies")){
//
//				}
				else{
					params.put(key.substring(4,key.length()-1),entry.getValue());

				}

			}

		}

		String message=UrlGetContent.getContent(apiUrl, params, "UTF-8",sb.toString());

    	PrintWriter printWriter = null;
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setHeader("Content-type",
				"application/json; charset=UTF-8");

		try {
			printWriter = httpServletResponse.getWriter();
			printWriter.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
    }


}
