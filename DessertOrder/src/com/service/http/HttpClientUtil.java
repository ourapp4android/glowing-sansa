package com.service.http;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	private String strUrl;
	private HttpPost httpPost;
	
	public String doPost(List<NameValuePair> params) {
		try {
			
			httpPost = new HttpPost(strUrl);
			// 设置字符集
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
			httpPost.setEntity(httpEntity);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpPost);
			// HttpStatus.SC_OK表示连接成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//取得返回的字符串
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStrUrl() {
		return strUrl;
	}

	public void setStrUrl(String strUrl) {
		this.strUrl = strUrl;
	}
}
