package com.running.together.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.running.together.AppConstant.AppConstant;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml.Encoding;

public class HttpCallback extends AsyncTask<String, Void, String> {
	// 将要连接的地址
	private String url = null;
	// 声明回调接口
	private MyCallback myCallback;
	//请求的内容
	private String request_content = "";
	//这里的int 是用来区请求的种类
	private int REQUEST_STYLE = -1;
	
	
//	private Map<String, String> pass_map = null;
//	private String post_url;

	// 传入两个参数的构造方法
	/**
	 * 
	 * @param url 请求路径
	 * @param myCallback 回调接口
	 */
	public HttpCallback(String url, MyCallback myCallback) {
		this.url = url;
		this.myCallback = myCallback;
	}
	/**
	 * 
	 * @param url 请求路径
	 * @param myCallback 回调接口
	 * @param request_content 请求内容
	 */
	public HttpCallback(String url,MyCallback myCallback,String request_content){
		this.url = url;
		this.myCallback = myCallback;
		this.request_content = request_content;
	}

//	// 传入post的路径
//	public HttpCallback(String url, MyCallback myCallback, String post_url) {
//		this.url = url;
//		this.myCallback = myCallback;
//		this.post_url = post_url;
//	}

//	// 传入三个参数（包含账号密码）的构造方法
//	public HttpCallback(String url, MyCallback myCallback,
//			final Map<String, String> pass_map) {
//		this.url = url;
//		this.myCallback = myCallback;
//		this.pass_map = pass_map;
//	}
//
	public HttpCallback(String url, MyCallback myCallback, int i) {
		this.url = url;
		this.myCallback = myCallback;
		REQUEST_STYLE = i;
		
	}
	@Override
	protected String doInBackground(String... params) {
		if(REQUEST_STYLE == -1){
			if("".equals(request_content)){
				return GetRequest();
			}else{
				return PostRequest();
			}
		}else if(AppConstant.GETMYRECORD == REQUEST_STYLE){
			return RequestMyRecord();
		}else{
			return null;
		}
		
		
//		String result = null;
//		if (null != pass_map) {
//			result = toLogin();
//			return result;
//		} else {
//			result = toLoginGet();
//			return result;
//		}
	}
/**
 * 请求个人记录
 * @return
 */
	private String RequestMyRecord() {
		// TODO Auto-generated method stub
		return null;
	}
	private InputStream RequestOne() {
		try {
			URL myurl = new URL(url);
			URLConnection conn = myurl.openConnection();
			InputStream is = conn.getInputStream();
			return is;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//
	private String PostRequest() {
		HttpURLConnection conn = null;
		try {
			URL theurl = new URL(url);
			conn = (HttpURLConnection) theurl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Fiddler");
		    conn.setRequestProperty("Content-Type", "application/json");
		    conn.setRequestProperty("Charset", "utf-8");
		    OutputStream os = conn.getOutputStream();
		    os.write(request_content.getBytes());
		    os.close();
		    //获取服务器返回的数据
		    if(conn.getResponseCode() == 200){
		    	  BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		    	    String retData = null;
		    	    String responseData = "";
		    	    while((retData = in.readLine()) != null)
		    	    {
		    	    responseData += retData;
		    	    }
		    	    return responseData;
		    }else{
		    	//如果服务器返回数据不是200，就返回500给请求方告知请求数据失败
		    	return "500";
		    }
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.d("TAG","MalformedURLException");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("TAG","IOException");
		}
		//如果上面的代码执行有问题，就返回"404"告知请求方出现异常
		return "404";
	}
	//
	private String GetRequest() {
		HttpClient mHttpClient =new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = mHttpClient.execute(get);
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "404";
	}

//	private String toLoginGet() {
//		HttpURLConnection conn = null;
//		StringBuffer sb;
//		try {
//
//			URL myurl = new URL(url);
//			conn = (HttpURLConnection) myurl.openConnection();
//			Log.d("test", "here");
//			conn.setReadTimeout(5000);
//			conn.setReadTimeout(5000);
//			conn.setRequestMethod("POST");
//			Log.d("test", "here1");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			// 向服务器传输参数
//			OutputStreamWriter osr = new OutputStreamWriter(
//					conn.getOutputStream());
//			BufferedWriter bw = new BufferedWriter(osr);
//			bw.write(post_url);
//			bw.flush();
//
//			InputStream is = conn.getInputStream();
//			Log.d("test", "here2");
//			InputStreamReader isr = new InputStreamReader(is);
//			Log.d("test", "here3");
//			BufferedReader br = new BufferedReader(isr);
//			String line = null;
//			sb = new StringBuffer();
//			Log.d("test", "here4");
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//			Log.d("test", "here5");
//			br.close();
//			isr.close();
//			is.close();
//			conn.disconnect();
//			Log.d("test", "here6");
//			return sb.toString();
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			System.out.println(">>>>" + "MalformedURLException");
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println(">>>>" + "IOException");
//		}
//		return null;
//	}
//
//	private String toLogin() {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//		for (String key : pass_map.keySet()) {
//			list.add(new BasicNameValuePair(key, pass_map.get(key)));
//		}
//		try {
//			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			if (httpResponse.getStatusLine().getStatusCode() == 200) {
//				String result = EntityUtils.toString(httpResponse.getEntity());
//				return result;
//			} else {
//				return "ERROR";
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			System.out.println("<<<<UnsupportedEncodingException");
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//			System.out.println("ClientProtocolException");
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("IOException");
//		}
//		return null;
//	}
//
//	private String toCommon() {
//		// 创建一个HttpClient
//		HttpClient client = new DefaultHttpClient();
//		// 获得一个get请求对象
//		HttpGet get = new HttpGet(url);
//		// 获得一个响应请求
//		HttpResponse response;
//		try {
//			response = client.execute(get);
//			// 得到响应码，成功为200
//			int code = response.getStatusLine().getStatusCode();
//			if (code == 200) {
//				// 得到返回信息，注意，一般还要将返回的信息通过输出流输出
//				String result = EntityUtils.toString(response.getEntity());
//				return result;
//			}
//
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}

	@Override
	protected void onPostExecute(String result) {
		// 请求成功则返回
		myCallback.callback(result);
		super.onPostExecute(result);
	}

	// 回调接口
	public interface MyCallback {
		void callback(String msg);
	}

}
