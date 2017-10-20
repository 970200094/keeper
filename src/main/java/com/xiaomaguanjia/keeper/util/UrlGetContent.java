package com.xiaomaguanjia.keeper.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;



public class UrlGetContent {

//	static  String cookies="JSESSIONID=03B28A078E64DC4A4A85DB891E0B4785; token=BXteCURHrBsXi4nv9sAs7r+ctGmF3rtd0t1sKhbQNVOsw/zo5bc/evdrJ2vnjBAAjoe6JN+EVXz0LU7qXjpEgtOhBQCesVT8SWdFN5LyI1UsWCKoNNJ2UvxkSgNPXV67aEkNmGN9lNN/CigYwpFmYbVyWkMjbx3Bs8CgylHP4FqegIXfJQdWGVndvamBzF7T+zIkl7ClMX4FEZAQNcvVLSn6RT761+iM5SSFbLt0cqS5wVhopwkzwf/rmK32XyIh3Jd2iKawuyKfMoEi+87CumD3S56W9QIzpW6TKwpYhJYOR2SQhyE0kzO1BJdh2tulW3cf9G8Ta7I7mlN3fX8VXSYGV8S+GLp88z/eogtCCrJ+bM2l5qPzfY8fcSmOQ4LrvFSA7w2vBzQDPG27vvh4TPFWYC1CFMgd/FnGJnw5jqKSzkPHSXwoeBUbWyAXDD1qneqAnoDxD0xrdV7Th1Xjtpiw/MzLQ7osc4wga0+MDC3uL1OrBwCt1imkCoMpFdn3X0KgGvkUS6R2evzmSE9zPXh+s+okuyrKb9FZd/yAeC+MUzTemn+pdsm9mwRToN3RcAQR+S0lcaYTy2TsGQy4XPbK67zuGg9uok2CU0FKXQxvJo9WbxMnZEfQylDiNn1a";
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UrlGetContent.class);


	/**
	 * get请求获取内容
	 *
	 * @param urlPath
	 * @param params
	 * @param enc
	 * @return
	 */
	public static String getContent(String urlPath, Map<String, Object> params, String enc, String cookies) {

		HttpURLConnection conn = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(getURL(urlPath, params, enc));
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(125000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", enc);
			if(cookies!=null){
				conn.setRequestProperty("Cookie",cookies);
				LOG.info("cookies=="+cookies);
			}

			conn.connect();






			isr = new InputStreamReader(conn.getInputStream(),"utf-8");

			int code=conn.getResponseCode();
			LOG.info("code=="+code);

			reader = new BufferedReader(isr);
			StringBuilder returnStr = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				returnStr.append(line);
			}
			return returnStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.disconnect();
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (isr != null)
							isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	private static String getURL(String urlPath, Map<String, Object> params, String enc) {
		StringBuffer buffer = new StringBuffer(urlPath);
		if (!urlPath.contains("?")) {
			buffer.append("?");
		} else {
			buffer.append("&");
		}
		buffer.append(getParams(params, enc));
		LOG.info("url="+buffer.toString());
		return buffer.toString();
	}

	/**
	 * post请求获取内容
	 *
	 * @param urlPath
	 * @param params
	 * @param enc
	 * @return
	 */
	public static String postContent(String urlPath, Map<String, Object> params, String enc) {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(55000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", enc);
			conn.connect();
			out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
			String body = getParams(params, enc);
			out.write(body);
			out.flush();

			isr = new InputStreamReader(conn.getInputStream());
			reader = new BufferedReader(isr);
			StringBuilder readJson = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				readJson.append(line);
			}
			return readJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();
					try {
						if (isr != null)
							isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * POST请求
	 */
	public static String postContent(String urlPath, String content, String enc) {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(25000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", enc);
			conn.connect();
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// String body = getParams(params, enc);
			out.write(content);
			out.flush();

			isr = new InputStreamReader(conn.getInputStream());
			reader = new BufferedReader(isr);
			StringBuilder readJson = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				readJson.append(line);
			}
			return readJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();
					try {
						if (isr != null)
							isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	// public static String URLPostRequest(String url, String param){
	// PrintWriter out = null;
	// BufferedReader in = null;
	// String result = "";
	// try {
	// URL realUrl = new URL(url);
	// // 打开和URL之间的连接
	// URLConnection conn = realUrl.openConnection();
	// // 设置通用的请求属性
	// // conn.setRequestProperty("accept", "*/*");
	// // conn.setRequestProperty("connection", "Keep-Alive");
	// // conn.setRequestProperty("user-agent",
	// // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	// // 发送POST请求必须设置如下两行
	// conn.setDoOutput(true);
	// conn.setDoInput(true);
	// // 获取URLConnection对象对应的输出流
	// out = new PrintWriter(conn.getOutputStream());
	// // 发送请求参数
	// out.print(param);
	// // flush输出流的缓冲
	// out.flush();
	// // 定义BufferedReader输入流来读取URL的响应
	// in = new BufferedReader(
	// new InputStreamReader(conn.getInputStream()));
	// String line;
	// while ((line = in.readLine()) != null) {
	// result += line;
	// }
	// } catch (Exception e) {
	// System.out.println("发送 POST 请求出现异常！"+e);
	// e.printStackTrace();
	// }
	// //使用finally块来关闭输出流、输入流
	// finally{
	// try{
	// if(out!=null){
	// out.close();
	// }
	// if(in!=null){
	// in.close();
	// }
	// }
	// catch(IOException ex){
	// ex.printStackTrace();
	// }
	// }
	// return result;
	// }

	/**
	 * 将map转换成url
	 *
	 * @param map
	 * @return
	 */
	private static String getParams(Map<String, Object> map, String enc) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				if (!StringUtils.isEmpty(entry.getKey()) && !StringUtils.isEmpty(entry.getValue().toString())) {
					sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), enc));
					sb.append("&");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

}
