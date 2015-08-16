package com.running.together.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.app.MyApplication;
import com.running.together.database.SQLiteHelper;
import com.running.together.model.UserInfo;
import com.running.together.util.AsyncImageLoader;
import com.running.together.util.AsyncImageLoader.ImageCallback;
import com.running.together.util.HttpCallback;
import com.running.together.util.HttpCallback.MyCallback;
import com.running.together.util.SharedPerferencesUtil;
import com.running.together.util.TimeRender;
import com.running.together.util.XmppTool;

@SuppressLint("NewApi")
public class ChattingActivity extends BaseActivity implements MyCallback{
	/*
	 * intent传过来的参数说明
	 * @friendname 对方用户名
	 * 
	 */
	private boolean isok;
	private MyAdapter adapter;
	private ListView listview;
	private List<Msg> listMsg = new ArrayList<Msg>();
	private String username,friendname,nickname,header,userurl;
	private String []arg;
	private EditText msgText;
	private XMPPConnection xmppconnection;
	private List<org.jivesoftware.smack.packet.Message> mlist;
	private TextView tdh;
    private Button reback,btsend;
    private Chat newchat;
    private org.jivesoftware.smack.ChatManager cm;
    private com.running.together.util.MyChatManagerListener my;
    SharedPerferencesUtil spUtil;
	MyApplication myApplication;
	private SQLiteHelper helper;
	public class Msg {
		String msg;
		String date;
		String from;
		public Msg( String msg, String date, String from) {
			this.msg = msg;
			this.date = date;
			this.from = from;
		}
	}
	public void initData(){
		this.xmppconnection = XmppTool.getConnection();
		//获取Intent传过来的用户名
		myApplication = MyApplication.getInstance();
		spUtil=myApplication.getSpUtil();
		this.username =spUtil.getUserID();
		this.friendname = getIntent().getStringExtra("friendname");
		arg=new String[]{username,friendname};
		Log.d("Chatting",username+"和"+friendname);
		this.isok=false;
		this.userurl=spUtil.getUserIcon();
		cm=XmppTool.getcm();
		my=XmppTool.getMy();
		my.setHandler(handler);
		Log.d("Chatting", my.getHandler());
		newchat=cm.createChat(friendname+"@"+AppConstant.OPENFIRE, null);//接收信息的用户+服务器名称
		helper = new SQLiteHelper(this);
	}
	public void initView(){
		listview = (ListView) findViewById(R.id.formclient_listview);
		msgText = (EditText) findViewById(R.id.formclient_text);
		tdh = (TextView)findViewById(R.id.tdh);
		reback = (Button)findViewById(R.id.login_reback_btn1);
		btsend = (Button) findViewById(R.id.formclient_btsend);
		
		listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		adapter= new MyAdapter(this);
		listview.setAdapter(adapter);
		//聊天管理
//		cm.addChatListener(new MyChatManagerListener());
		//获取文本信息
		reback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish(); 
			}
		});
		btsend.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//获取text文本
						String msg = msgText.getText().toString();
						if(msg.length()> 0){			
							try {
								//发送消息
								newchat.sendMessage(msg);
								Msg mmsg=new Msg(msg, TimeRender.getDate(), "OUT");
								listMsg.add(mmsg);
								//刷新适配器
								adapter.notifyDataSetChanged();
								helper.update(arg, msg, TimeRender.getDate(),0);
								helper.insertmsg(spUtil.getUserID(),friendname, mmsg.msg, mmsg.date,"OUT");
							} 
							catch (XMPPException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							Toast.makeText(ChattingActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
						}
						//清空text
						msgText.setText("");
					}
				});
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chatting);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initData();
		initView();
//		offline();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Cursor cursor=helper.query1(arg);
		if(cursor.getCount()!=0){
			cursor.moveToFirst();
			nickname=cursor.getString(cursor.getColumnIndex("Fnickname")); 
		    header=cursor.getString(cursor.getColumnIndex("Furl"));
		    this.isok=true;
			tdh.setText(nickname);
		}
		else{
			//新加入的聊天
			new HttpCallback(AppConstant.GETMSG+"?username="+friendname,this,"").execute();
		}
		listMsg=getmsg(helper.querymsg1(arg));
		adapter.notifyDataSetChanged();
		spUtil.setFriendID(friendname);
		my.setHandler(handler);
	}
	//退出
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
//		XmppTool.closeConnection();
//		android.os.Process.killProcess(android.os.Process
//				.myPid());
	}
	class MyAdapter extends BaseAdapter {

		private Context cxt;
		private LayoutInflater inflater;

		public MyAdapter(ChattingActivity formClient) {
			this.cxt = formClient;
		}

		@Override
		public int getCount() {
			return listMsg.size();
		}

		@Override
		public Msg getItem(int position) {
			return listMsg.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			Log.d("getView", "ff"+position);
			//显示消息的布局：内容、背景、用户、时间
			this.inflater = (LayoutInflater) this.cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			String url;
			//IN,OUT的图片
			if(listMsg.get(position).from.equals("IN"))
			{	
				convertView = this.inflater.inflate(R.layout.formclient_chat_in, null);
				url=header;
			}
			else
			{	
				convertView = this.inflater.inflate(R.layout.formclient_chat_out, null);
				url=userurl;
			}
			ImageView imageview=(ImageView) convertView.findViewById(R.id.image);
			Bitmap head_image = AsyncImageLoader.loadBitmap(0,
					url,imageview, position,
					new ImageCallback() {
						public void imageSet(Bitmap bitmap, ImageView iv) {
							iv.setImageBitmap(bitmap);
						}
					});
//			TextView dateView = (TextView) convertView.findViewById(R.id.formclient_row_date);
			TextView msgView = (TextView) convertView.findViewById(R.id.formclient_row_msg);
			if(head_image!=null)imageview.setImageBitmap(head_image);
//			dateView.setText(listMsg.get(position).date);
			msgView.setText(listMsg.get(position).msg);
			return convertView;
		}
	}
	@Override
	public void callback(String msg) {
		// TODO Auto-generated method stub
		//设置聊天对方的昵称头像，并表示状态（离线加载要判断状态）,并保存到数据库
		Gson gson=new Gson();
		System.out.println(msg);
		Map<String,String> map=gson.fromJson(msg, Map.class);
		Log.d("TAG", map.get("username"));
		Log.d("TAG", map.get("nickname"));
		Log.d("TAG", map.get("usericon"));
		helper.insert(
				username, 
				map.get("username"), 
				"这只熊",
				map.get("usericon"), 
				"", 
				TimeRender.getDate());
		tdh.setText(nickname);
		this.isok=true;
	}
	Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) 
			{			
				switch (msg.what) {
				case 1:
					//获取消息并显示
					String[] args = (String[]) msg.obj;
					Log.d("chating", args[0]);
					Msg mmsg=new Msg(args[0], args[1], args[2]);
					listMsg.add(mmsg);
					//刷新适配器
					adapter.notifyDataSetChanged();
					helper.update(arg,args[0], args[1],0);
					helper.insertmsg(spUtil.getUserID(),friendname, mmsg.msg, mmsg.date,"IN");
					break;
				default:
					break;
				}
			};
	};
	private List<Msg> getmsg(Cursor c){
		List <Msg> l=new ArrayList<Msg>();
		while(c.moveToNext()){
			Msg msg=new Msg(
					c.getString(c.getColumnIndex("Ftext")), 
					c.getString(c.getColumnIndex("Ftime")), 
					c.getString(c.getColumnIndex("Frome")));
			l.add(msg);
		}
		return l;
	}
}
