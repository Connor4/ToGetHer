package com.running.together.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

public class XmppTool {

	private static XMPPConnection con = null;
    private static org.jivesoftware.smack.ChatManager cm=null;
    private static MyChatManagerListener my=null;
	public static MyChatManagerListener getMy() {
		return my;
	}
	private static void openConnection() {
		try {
			//url、端口，也可以设置连接的服务器名字，地址，端口，用户。
			ConnectionConfiguration connConfig = new ConnectionConfiguration("wangziqing",5222);
			connConfig.setReconnectionAllowed(true);      
			connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);       
			connConfig.setSASLAuthenticationEnabled(false);
			connConfig.setSendPresence(false); 

			connConfig.setTruststorePath("/system/etc/security/cacerts.bks");
			connConfig.setPKCS11Library("changeit");
			connConfig.setTruststoreType("bks");
			con = new XMPPConnection(connConfig);
			con.connect();
			Log.i("XmppConnection", "建立连接");
		}
		catch (XMPPException xe) 
		{
			xe.printStackTrace();
			
		}
	}
	private static void creatcm(){
		if(con==null){
			openConnection();
		}
		cm=con.getChatManager();
		my=new MyChatManagerListener();
		cm.addChatListener(my);
		Log.i("XmppConnection", "建立管理");
	}

	public static XMPPConnection getConnection() {
		if (con==null) {
			openConnection();
		}
		Log.i("XmppConnection", "获取连接");
		return con;
	}

	public static void closeConnection() {
		con.disconnect();
		con = null;
		Log.i("XmppConneCtion", "关闭连接");
	}

	public static org.jivesoftware.smack.ChatManager getcm() {
		if(cm==null){
			creatcm();
		}
		Log.i("XmppConnection", "或取管理");
		return cm;
	}

}