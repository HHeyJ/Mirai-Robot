package com.hyq.robot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
public class NetworkUtil {

	/**
	 * 获取URL输入流
	 * @param URL
	 * @return
	 */
	public static InputStream getInputStream(String URL){
		HttpsURLConnection conn = null;
		try {
			URL URL_Object = new URL(URL);
			conn = (HttpsURLConnection) URL_Object.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10 * 1000);
			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(conn.getInputStream(), output);
			return new ByteArrayInputStream(output.toByteArray());
		} catch (IOException e) {
			log.error("获取URL输入流发生IO异常",e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
}
