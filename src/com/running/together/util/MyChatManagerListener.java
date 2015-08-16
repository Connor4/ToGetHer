package com.running.together.util;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.os.Handler;
import android.util.Log;

import com.running.together.AppConstant.AppConstant;
import com.running.together.app.MyApplication;

public class MyChatManagerListener implements ChatManagerListener{
	private Handler handler;
	public String getHandler() {
		return handler.toString();
	}
	public void setHandler(Handler handler) {
		Log.e("listener","绑定handler");
		this.handler = handler;
	}
	SharedPerferencesUtil spUtil;
	//
	MyApplication myApplication;
	public MyChatManagerListener(){
		Log.e("listener","建立监听");
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
	}
	@Override
	public void chatCreated(Chat chat, boolean able) 
	{
		chat.addMessageListener(new MessageListener() {
			@Override
			public void processMessage(Chat chat2, Message message)
			{
				Log.v("--tags--", "--tags-form--"+message.getFrom());
				Log.v("--tags--", "--tags-message--"+message.getBody());
				//收到来自water-pc服务器water的消息（获取自己的服务器，和好友）
				Log.d("sd", spUtil.getFriendID());
					int i= message.getFrom().indexOf("@");
					String name=message.getFrom().substring(0, i);
					Log.d("meaa",name);
					String[] args = new String[] {message.getBody(), TimeRender.getDate(), "IN",name};
					Log.d(name, args.toString());
					//在handler里取出来显示消息
					android.os.Message msg = handler.obtainMessage();
					msg.what = 3;
					msg.obj = args;
					msg.sendToTarget();	
				if(message.getFrom().contains(spUtil.getFriendID()))
					{
						//获取用户、消息、时间、IN
						int i1= message.getFrom().indexOf("@");
						String name1=message.getFrom().substring(0, i1);
						Log.d("meaa",name1+"dfs");
						String[] args1 = new String[] {message.getBody(), TimeRender.getDate(), "IN",name1};
						Log.d(name1, args1.toString());
						//在handler里取出来显示消息
						android.os.Message msg1 = handler.obtainMessage();
						msg1.what = 1;
						msg1.obj = args1;
						msg1.sendToTarget();	
					}
				
			}
		});
	}
}
