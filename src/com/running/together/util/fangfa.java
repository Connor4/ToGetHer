package com.running.together.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.search.UserSearchManager;

import android.util.Log;

import com.running.together.model.User;

public class fangfa {
//登录
	public static boolean denglv(String user,String password){
		try {
			if(XmppTool.getConnection()==null)
				return false;
			SASLAuthentication.supportSASLMechanism("PLAIN",0);			
			XmppTool.getConnection().login(user, password);
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			XmppTool.closeConnection();
			e.printStackTrace();
			
		}
		return false;
		
	}
//注册	
	public static String regist(String account,String password)
	{
		if(XmppTool.getConnection()==null)
			return "0";
		Registration reg = new Registration();
		reg.setType(IQ.Type.SET);
		reg.setTo(XmppTool.getConnection().getServiceName());
		reg.setUsername(account);
		reg.setPassword(password);
		reg.addAttribute("android", "geolo_createUser_android");
		
		PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()),new PacketTypeFilter(IQ.class));
		PacketCollector collector = XmppTool.getConnection().createPacketCollector(filter);
		XmppTool.getConnection().sendPacket(reg);
		IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());
		
		collector.cancel();
		if(result==null){
			Log.e("zhece", "No response from server");
			return "0";
		}else if(result.getType()==IQ.Type.RESULT){
			return "1";
		}else if(result.getError().toString().equalsIgnoreCase("Conflict(409)")){
			Log.e("zhuce", "IQ.Type.ERROR");
			return "2";
		}else{
			Log.e("zhuce", "IQ.Tyep.ERROR"+result.getError().toString());
			return "3";
		}
		
		
	}
	//查找用户
	public List<User> searchUsers(XMPPConnection connection,String serverDomain,
			String username)throws XMPPException{
		List<User> results = new ArrayList<User>();
		System.out.println("查询开始。。。。。"+connection.getHost()+connection.getServiceName());
		UserSearchManager usm = new UserSearchManager(connection);
		Form searchForm = usm.getSearchForm(serverDomain);
		Form answerForm = searchForm.createAnswerForm();
		answerForm.setAnswer("Username", true);
		
		answerForm.setAnswer("search", username);
		
		ReportedData data = usm.getSearchResults(answerForm, serverDomain);
		
		Iterator<Row> it = data.getRows();
		Row row = null;
		User user = null;
		while(it.hasNext()){
			user = new User();
			row = it.next();
			user.setUsername(row.getValues("Username").next().toString());
			user.setUsername(row.getValues("Name").next().toString());
		
			results.add(user);
			
		}
		
		return results;
		
	}
	//添加好友
	public boolean addUser(Roster roster,String usename,String name,String groupName){
		
		try{
			roster.createEntry(usename, name, null);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	//查找好友
	public List<RosterEntry> getAllEntries(Roster roster) {
		   List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
		   Collection<RosterEntry> rosterEntry = roster.getEntries();
		   Iterator<RosterEntry> i = rosterEntry.iterator();
		   while (i.hasNext()) {
		    Entrieslist.add(i.next());
		   }
		   return Entrieslist;
		  }
	//接受离线信息
	public static List<org.jivesoftware.smack.packet.Message> getOffLine() {  
	       List<org.jivesoftware.smack.packet.Message> msglist = new ArrayList<org.jivesoftware.smack.packet.Message>();  
	        // 获取离线消息,线程阻塞 不能Toast  
	       OfflineMessageManager offline = new OfflineMessageManager(XmppTool.getConnection());
	       
	        try {  
	        	System.out.println(offline.getMessageCount());
	            Iterator<org.jivesoftware.smack.packet.Message> it = offline.getMessages();  
	            while (it.hasNext()) {  
	                org.jivesoftware.smack.packet.Message message = it.next();  
	               msglist.add(message);  
	            }  
	        } catch (Exception e) {  
	         e.printStackTrace();  
	        } finally {  
	           try {  
	               // 设置在线  
	                Presence presence = new Presence(Presence.Type.available);  
	               XmppTool.getConnection().sendPacket(presence);  
	                offline.deleteMessages(); 
	            } catch (XMPPException e) {  
	                // TODO Auto-generated catch block  
	               e.printStackTrace();  
	            }  
	        }  
	        return msglist;  
	    }  

}
