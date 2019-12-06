package com.ymw.love.system.config.secret;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.util.RSAEncrypt;
import com.ymw.love.system.util.Signature;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.config.HintTitle;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年10月24日 上午11:01:18 类说明：
 */
public class DecryptHttpInputMessage implements HttpInputMessage {

	private HttpHeaders headers;
	private InputStream body;

	public DecryptHttpInputMessage(HttpInputMessage inputMessage, Authority authority, HttpServletRequest request,
			String privateKey, String rule) {

		// 获取请求内容
		this.headers = inputMessage.getHeaders();

		StringBuilder content = new StringBuilder();

		BufferedReader bufferedReader;

		try {

			bufferedReader = new BufferedReader(new InputStreamReader(inputMessage.getBody()));

			String text;
			while ((text = bufferedReader.readLine()) != null) {
				content.append(text);
			}

		} catch (IOException e) {
			throw new MissRequiredParamException(SystemEnum.DECODE_ERROR, HintTitle.System.decode_error);
		}

		// 解密
		if (authority.des()) {

			JSONObject json = JSON.parseObject(content.toString());
			try {
				String de = RSAEncrypt.decrypt(json.getString("mi"), privateKey);
				content = new StringBuilder(de);
			} catch (Exception e) {
				throw new MissRequiredParamException(SystemEnum.DECODE_ERROR, HintTitle.System.decode_error);
			}

		}
		
		//签名验证
		if(authority.sign()) {
			signVerify(request, rule, content.toString());
		}
		
		
		this.body = getInputStreamS(content.toString().getBytes());

	}

	@Override
	public HttpHeaders getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}

	@Override
	public InputStream getBody() throws IOException {
		// TODO Auto-generated method stub
		return body;
	}

	private void signVerify(HttpServletRequest request,String rule,String signData) {
		// 设备编号
		String plant = request.getHeader("plant");
		if (StringUtils.isEmpty(plant)) {
			throw new MissRequiredParamException(HintTitle.System.encrypt_error);
		}
		//解密
		try {
			plant = new String(Base64.getDecoder().decode(plant), "UTF-8");
		} catch (Exception e) {
			throw new MissRequiredParamException(HintTitle.System.plant_error);
		}
        
		// 小于14位
		if (plant.length() < 14) {
			throw new MissRequiredParamException(HintTitle.System.plant_error);
		}

		
		//获取数据签名
		String sign1=request.getHeader("diffuse");
		if(StringUtils.isEmpty(sign1)) {
			throw new 	MissRequiredParamException(SystemEnum.SING_ERROR,HintTitle.System.signature_error);
		}
		//签名验证
		String sign2 = Signature.getSignJson(signData,  plant.substring(0,  Integer.parseInt(rule)));
		
		if(!sign1.toUpperCase().equals(sign2)) {
			throw new 	MissRequiredParamException(SystemEnum.SING_ERROR,HintTitle.System.signature_error);
		}
		
	}

	private ServletInputStream getInputStreamS(byte[] requestBody) {

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// do nothing
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}

}
