package com.running.together.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;

public class SaveBitmap {
	/**
	 * 传入保存图片的参数
	 * @param bitmap Bitmap对象
	 * @param dir	  文件的绝对路径 Environment.getStrogeDrictory().getAbsolutePath()+"/"+"文件夹"+"/";
	 * @param path 	  文件的名称 dir+name.png;
	 */
	public static void save(Bitmap bitmap,String dir,String path){
		try {
			File filedir = new File(dir);
			if(!filedir.exists()){
				filedir.mkdir();//路径不存在就创建
			}
			File file = new File(path);
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*Bitmap bitmap = null;
	String dir = null;
	String path = null;
	*//**
	 * 传入保存图片的参数
	 * @param bitmap Bitmap对象
	 * @param dir	  文件的绝对路径 Environment.getStrogeDrictory().getAbsolutePath()+"/"+"文件夹"+"/";
	 * @param path 	  文件的名称 dir+name.png;
	 *//*
	public saveBitmap(Bitmap bitmap,String dir,String path){
		this.bitmap = bitmap;
		this.dir = dir;
		this.path = path;
	}
	public void save(){
		try {
			File filedir = new File(dir);
			if(!filedir.exists()){
				filedir.mkdir();//路径不存在就创建
			}
			File file = new File(path);
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
