package com.running.together.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.AsyncTask;

import com.running.together.myinterface.HttpStringInterface;

public class HttpStringCallbcak extends AsyncTask<String, Void, String> {
	private HttpStringInterface userInfoCallback;
	private String request_url;
	private String resultData = "";

	public HttpStringCallbcak(String request_url,
			HttpStringInterface userInfoCallback) {
		this.request_url = request_url;
		this.userInfoCallback = userInfoCallback;
	}

	@Override
	protected String doInBackground(String... params) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(request_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5*1000);
			conn.setRequestProperty("Charset", "utf-8");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
			InputStreamReader is;
			BufferedReader br;
			if (conn.getResponseCode() == 200) {
				is = new InputStreamReader(conn.getInputStream());
				br = new BufferedReader(is);
				String inputLine = null;
				while (((inputLine = br.readLine()) != null)) {
					resultData += inputLine;
				}
			} else {
				return null;
			}
			is.close();
			br.close();
			conn.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultData;
	}

	@Override
	protected void onPostExecute(String result) {
		userInfoCallback.GetStringCallbcak(result);
		super.onPostExecute(result);
	}
	

}
