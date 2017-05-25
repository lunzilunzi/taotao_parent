package com.taotao.portal.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {

	@Test
	public void  doGet() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个GET对象
		HttpGet get = new HttpGet("http://www.sogou.com");
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 去响应的结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("响应状态码:"+statusCode);
		HttpEntity entity = response.getEntity();
		String s = EntityUtils.toString(entity, "utf-8");
		System.out.println(s);
		// 关闭httpClient
		response.close();
		httpClient.close();
	}

	@Test
	public void doGEtWithParam() throws Exception{
		// 创建一个httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个url对象
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("query", "花千骨");
		HttpGet get = new HttpGet(uriBuilder.build());
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 去响应的结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("响应状态码:"+statusCode);
		HttpEntity entity = response.getEntity();
		String s = EntityUtils.toString(entity, "utf-8");
		System.out.println(s);
		// 关闭httpClient
		response.close();
		httpClient.close();
	}

	@Test
	public void doPost() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个post对象
		HttpPost post = new HttpPost("http://localhost:8082/httpClient/post.html");
		// 执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		String s = EntityUtils.toString(response.getEntity());
		System.out.println(s);
		// 关闭资源
		response.close();
		httpClient.close();

	}

	@Test
	public void doPostWithParam() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个post对象
		HttpPost post = new HttpPost("http://localhost:8082/httpClient/post.action");
		/* 模拟一个表单 */
		//创建一个post对象
		List<NameValuePair> kvlist = new ArrayList<>();
		kvlist.add(new BasicNameValuePair("username", "张三"));
		kvlist.add(new BasicNameValuePair("password", "123456"));
		// 包装成一个Entity对象
		StringEntity entity = new UrlEncodedFormEntity(kvlist, "utf-8");
		//设置请求内容
		post.setEntity(entity);
		// 执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		String s = EntityUtils.toString(response.getEntity());
		System.out.println("s"+s);
		// 关闭资源
		response.close();
		httpClient.close();

	}
}










