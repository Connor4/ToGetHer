package com.running.together.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.running.together.R;

public class Info implements Serializable
{
	private double latitude;
	private double longitude;
	private int imgId;
	private String sex;
	private String name;
	private String loc;
	private String content;
	private String time;

	public static List<Info> infos = new ArrayList<Info>();

	static
	{
		infos.add(new Info(23.055687, 113.410272, R.drawable.yue_map_user1,
				"male", "这只熊", "广东工业大学", "我想去跑步", "1分钟前"));
		infos.add(new Info(23.050566, 113.395468, R.drawable.yue_map_user2,
				"male", "这只猫", "华南理工大学", "我想去唱K", "30分钟前"));
		infos.add(new Info(23.04744, 113.399277, R.drawable.yue_map_user3,
				"female", "这只狗", "广州药学院", "我想打羽毛球", "45分钟前"));
		infos.add(new Info(23.062537, 113.395684, R.drawable.yue_map_user4,
				"male", "这只猪", "广中医", "我想打篮球", "1小时前"));
	}

	public Info(double latitude, double longitude, int imgId, String sex,
			String name, String loc, String content, String time)
	{
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.loc = loc;
		this.content = content;
		this.sex = sex;
		this.time = time;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getImgId()
	{
		return imgId;
	}

	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLoc()
	{
		return loc;
	}

	public void setLoc(String loc)
	{
		this.loc = loc;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

}
