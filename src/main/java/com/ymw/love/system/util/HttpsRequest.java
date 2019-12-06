package com.ymw.love.system.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsRequest {

	/**
	 * 处理https GET/POST/PUT/DELETE请求 请求地址、请求方法、参数
	 * <p>
	 * 返回map： code、arg
	 * </p>
	 * 
	 */
	public Map<String, Object> httpsRequestMap(String requestUrl, String requestMethod, String outputStr,
			String token) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 创建SSLContext
			SSLContext sslContext = SSLContext.getInstance("SSL");
			TrustManager[] tm = { new MyX509TrustManager() };
			// 初始化
			sslContext.init(null, tm, new SecureRandom());
			// 获取SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			if (StringUtils.Equals("POST", requestMethod) || StringUtils.Equals("PUT", requestMethod)
					|| StringUtils.Equals("DELETE", requestMethod)) {
				conn.setDoOutput(true);
				conn.setDoInput(true);

				if (StringUtils.Equals("POST", requestMethod)) {
					conn.setUseCaches(false);
				}
				conn.setRequestMethod(requestMethod);
				conn.setRequestProperty("Content-type", "application/json");
			}

			if (StringUtils.isNotEmpty(token)) {
				conn.setRequestProperty("Authorization", "Bearer " + token);
			}
			// 设置当前实例使用的SSLSoctetFactory
			conn.setSSLSocketFactory(ssf);
			conn.connect();
			if (StringUtils.isNotEmpty(outputStr)) {
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}

			if (conn.getResponseCode() == 200) {
				// 读取服务器端返回的内容
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				map.put("arg", buffer.toString());
				map.put("code", conn.getResponseCode());
				br.close();
				is.close();
				isr.close();
			} else if (conn.getResponseCode() == 401) {
				map.put("code", 401);
			} else if (conn.getResponseCode() == 400) {
				map.put("code", 400);
			} else if (conn.getResponseCode() == 403) {
				map.put("code", 403);
			} else {
				map.put("code", conn.getResponseCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 处理https GET/POST/PUT/DELETE请求 请求地址、请求方法、参数
	 * <p>
	 * 返回map： code、arg
	 * </p>
	 * 
	 */
	public String httpRequest(String requestUrl, String requestMethod, String outputStr, String token) {

		try {

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			if (StringUtils.Equals("POST", requestMethod) || StringUtils.Equals("PUT", requestMethod)
					|| StringUtils.Equals("DELETE", requestMethod)) {
				conn.setDoOutput(true);
				conn.setDoInput(true);

				if (StringUtils.Equals("POST", requestMethod)) {
					conn.setUseCaches(false);
				}
				conn.setRequestMethod(requestMethod);
				conn.setRequestProperty("Content-type", "application/json");
			} else {
				conn.setRequestMethod(requestMethod);
			}

			if (StringUtils.isNotEmpty(token)) {
				conn.setRequestProperty("Authorization", "Bearer " + token);
			}

			conn.connect();
			if (StringUtils.isNotEmpty(outputStr)) {
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			br.close();
			is.close();
			isr.close();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String httpsRequest(String requestUrl, String requestMethod, String outputStr, String token) {
		Map<String, Object> map = httpsRequestMap(requestUrl, requestMethod, outputStr, token);
		if (Integer.parseInt(map.get("code").toString()) == 200) {
			return map.get("arg").toString();
		}
		return null;
	}

	/**
	 * 下载聊天文件
	 * 
	 * @return
	 */
	public String downloadRecordFile(String requestUrl) {
		try {

			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(6 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.connect();
			 String arg= null;
			if(conn.getResponseCode()==200) {
				InputStream is = conn.getInputStream();
				arg=JYzip(is);
				is.close();
			}
				
           return arg;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String JYzip(InputStream in) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		 GZIPInputStream gis = new GZIPInputStream(in);
		 int len1 = -1;
	        byte[] b1 = new byte[1024];
	        while ((len1 = gis.read(b1)) != -1) {
	            byteArrayOutputStream.write(b1, 0, len1);
	        }
	        byteArrayOutputStream.close();
	return byteArrayOutputStream.toString();
	}
	
	
	
	public class MyX509TrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}

	public static void main(String[] args) {

		// String s=httpsRequest(
		// ConstantUtil.CONSOLE+"/users","POST","{\"username\":\"jliu\",\"password\":\"123456\"}",null);
		// System.out.println(s);
	}

}
