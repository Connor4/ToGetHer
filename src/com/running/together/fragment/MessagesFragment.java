package com.running.together.fragment;

import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.running.together.R;
import com.running.together.adapter.ChattingListViewAdapter;
import com.running.together.app.MyApplication;
import com.running.together.database.SQLiteHelper;
import com.running.together.util.SharedPerferencesUtil;
import com.running.together.util.TimeRender;
import com.running.together.util.XmppTool;
import com.running.together.util.fangfa;

public class MessagesFragment extends Fragment implements OnClickListener{
	private List<org.jivesoftware.smack.packet.Message> mlist;
	private Activity activity;
	private Button chat;
	private Button login,delete;
	private ListView listview;
	private SQLiteHelper helper;
	private final String TAG="MessageFragment";
	private SharedPerferencesUtil spUtil;
	private MyApplication myApplication;
	private Cursor cursor; 
	private ChattingListViewAdapter chattingadapter;
	private com.running.together.util.MyChatManagerListener my;
    private String[] arg;
    private org.jivesoftware.smack.ChatManager cm;
    private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) 
		{			
			switch (msg.what) {
			case 3:
				//鑾峰彇娑堟伅骞舵樉绀�
				String[] args = (String[]) msg.obj;
				arg=new String[]{
						spUtil.getUserID(),args[3]
				};
				helper.update(arg, args[0],args[1],1);
				helper.insertmsg(spUtil.getUserID(), args[3],  args[0], args[1], "IN");
				cursor=helper.query(new String[]{spUtil.getUserID()});
				chattingadapter.cursor=cursor;
				chattingadapter.notifyDataSetChanged();
				Log.d(TAG, args[0]);
				break;
			case 2:
				Log.d(TAG, (String[])msg.obj+"閫氱煡");
				String []ss=(String[])msg.obj;
				String []s={
						spUtil.getUserID(),
						ss[3]
				};
				helper.update(s,ss[0],ss[1],1);
				helper.insertmsg(spUtil.getUserID(), ss[3], ss[0], ss[1], "IN");
				arg=new String[]{
					spUtil.getUserID()
				};
				Log.d(TAG, "cha"+arg[0]);
				cursor=helper.query(arg);
				chattingadapter.cursor=cursor;
		    	Log.d("size",cursor.getCount()+"");
				chattingadapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity=this.getActivity();
		View view= inflater.inflate(R.layout.messages_layout, container, false);
		Log.d(TAG,"init()");
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		if(spUtil.getUserID()!=null){
			cm=XmppTool.getcm();
			my=XmppTool.getMy();
			iniData(view);
		}
		iniView(view);
		return view;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
////				offline();
//			}
//		}).start();
		if(spUtil.getUserID()!=null){
			my.setHandler(handler);
			Log.d(TAG, my.getHandler());
			arg=new String[]{
					spUtil.getUserID()
				};
			cursor=helper.query(arg);
			chattingadapter.cursor=cursor;
			chattingadapter.notifyDataSetChanged();
		}
	}
	public void iniData(View view){
		helper = new SQLiteHelper(activity); 
		arg=new String[]{spUtil.getUserID()};
		cursor=helper.query(arg);
		try {
			chattingadapter = new ChattingListViewAdapter(activity, cursor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
//		case R.id.chat:
//			//娣诲姞
//			Log.d(TAG, "chat");
//			helper.insert(spUtil.getUserID(), "002", "闆堕浂浜�","http://10.10.116.80:8080/Run/upload/head/header.jpg", "text", "time");
//			break;
//		case R.id.delete:
//			//娣诲姞
//			Log.d(TAG, "delete");
//			activity.deleteDatabase("ToGetHer");
//			break;
//		case R.id.login:
//			//鏄剧ず
//			String args[]=new String[]{"001"};
//			cursor=helper.query(args);
//			try {
//				chattingadapter = new ChattingListViewAdapter(activity, cursor);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			listview.setAdapter(chattingadapter);
//			break;
		default:
			break;
		}
		
	}
	public void iniView(View view){
//		chat=(Button) view.findViewById(R.id.chat);
//		delete=(Button) view.findViewById(R.id.delete);
//		login=(Button) view.findViewById(R.id.login);
		listview=(ListView) view.findViewById(R.id.message_listview);
		listview.setAdapter(chattingadapter);
//		delete.setOnClickListener(this);
//		chat.setOnClickListener(this);
//		login.setOnClickListener(this);
	}
	public void offline(){
		//绂荤嚎鍔犺浇娑堟伅,淇敼鏈湴鏁版嵁搴�
		mlist = fangfa.getOffLine();
		for(int j=0;j<mlist.size();j++){
			Message message = mlist.get(j);
			int i= message.getFrom().indexOf("@");
			String name=message.getFrom().substring(0, i);
			String[] args = new String[] {message.getBody(), TimeRender.getDate(),"IN",name};
			Log.d(TAG, "arg:"+arg.toString());
			Log.d(TAG, "text:"+args[0]);
			Log.d(TAG, "time:"+args[1]);
			android.os.Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj=args;
			msg.sendToTarget();	
		}
	}

}
