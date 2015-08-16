package com.running.together.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.running.together.adapter.ActionApplyListViewAdapter;
import com.running.together.adapter.MyActionListViewAdapter;
import com.running.together.adapter.MyGrideViewAdapter;
import com.running.together.adapter.MyGroupListViewAdapter;
import com.running.together.adapter.MyTopicListViewAdapter;

public class Download extends Thread {
	public MyGrideViewAdapter myAdapter;
	private MyActionListViewAdapter adapter;
	private MyGroupListViewAdapter myGroupListViewAdapter;
	private MyTopicListViewAdapter myTopicListViewAdapter;
	private ActionApplyListViewAdapter actionApplyListViewAdapter;
	public String url;
	private String sdcardurl;
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				myGroupListViewAdapter.notifyDataSetChanged();
				break;
			case 1:
				myAdapter.notifyDataSetChanged();
				break;
			case 2:
				adapter.notifyDataSetChanged();
				break;
			case 3:
				myTopicListViewAdapter.notifyDataSetChanged();
				break;
			case 4:
				actionApplyListViewAdapter.notifyDataSetChanged();
				break;
			}

		}

	};

	public Download(MyGrideViewAdapter myAdapter, String url) {
		this.myAdapter = myAdapter;
		this.url = url;
		// 即将存入SD卡的路径
		try {
			sdcardurl = Environment.getExternalStorageDirectory()
					.getCanonicalPath() + "/" + url.hashCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Download(MyActionListViewAdapter adapter, String url) {
		this.adapter = adapter;
		this.url = url;
		// 即将存入SD卡的路径
		try {
			sdcardurl = Environment.getExternalStorageDirectory()
					.getCanonicalPath() + "/" + url.hashCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Download(MyGroupListViewAdapter myGroupListViewAdapter, String url) {
		this.myGroupListViewAdapter = myGroupListViewAdapter;
		this.url = url;
		try {
			sdcardurl = Environment.getExternalStorageDirectory()
					.getCanonicalPath() + "/" + url.hashCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Download(MyTopicListViewAdapter myTopicListViewAdapter, String url) {
		this.myTopicListViewAdapter = myTopicListViewAdapter;
		this.url = url;
		try {
			sdcardurl = Environment.getExternalStorageDirectory()
					.getCanonicalPath() + "/" + url.hashCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Download(ActionApplyListViewAdapter actionApplyListViewAdapter,
			String url) {
		this.actionApplyListViewAdapter = actionApplyListViewAdapter;
		this.url = url;
		try {
			sdcardurl = Environment.getExternalStorageDirectory()
					.getCanonicalPath() + "/" + url.hashCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		URL murl;
		try {
			murl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();

			FileOutputStream fos = new FileOutputStream(sdcardurl);
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > -1) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
			conn.disconnect();
			if (myGroupListViewAdapter != null) {
				handler.sendEmptyMessage(0);
			} else if (myAdapter != null) {
				handler.sendEmptyMessage(1);
			} else if (adapter != null) {
				handler.sendEmptyMessage(2);
			} else if (myTopicListViewAdapter != null) {
				handler.sendEmptyMessage(3);
			} else if (actionApplyListViewAdapter != null) {
				handler.sendEmptyMessage(4);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("TAG", "出现异常:" + e.getMessage());

		}

	}

}
