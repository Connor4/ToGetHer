package com.running.together.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class HttpImageCallback extends AsyncTask<String, Void, Bitmap>{
	private IconCallback iconCallback;
	private String target_url;
	public HttpImageCallback(String target_url,IconCallback iconCallback){
		this.iconCallback = iconCallback;
		this.target_url = target_url;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		try {
			URL myurl = new URL(target_url);
			HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			if(conn.getResponseCode() == 200){
			 if(bitmap != null){
				 bitmap.recycle();
				 System.gc();
			 }
			 InputStream is = conn.getInputStream();
			 bitmap = BitmapFactory.decodeStream(is);
			 return bitmap;
			}else{
				System.out.println("返回码不是200");
			}
			return null;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		iconCallback.getIcon(result);
	}
	public interface IconCallback{
		void getIcon(Bitmap bitmap);
	}

}
