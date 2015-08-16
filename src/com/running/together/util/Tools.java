package com.running.together.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Tools {

	public static String formatDate(String date_str) {
		Date date = new Date(date_str);
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		return formatter.format(date);
	}

	/**
	 * 通过url 获得对应的Drawable资源
	 * 
	 * @param flag
	 *            是否压缩标记
	 * @param url
	 *            图片地址
	 * @return
	 */
	public static Bitmap getBitmapFromUrl(int flag, String url) {
		try {
			URLConnection urlc = new URL(url).openConnection();
			URLConnection urlc2 = new URL(url).openConnection();
			//ʹ��HttpURLConnection��������ֻ��ʹ��һ��
			//�������������������� �������³�ʼ��
			if (flag == 0) {
				return Tools.decodeSampledBitmapFromStream(
						urlc.getInputStream(), urlc2.getInputStream(),50,50);
			} else if (flag == 1) {
				return Tools
						.decodeSampledBitmapFromStream(urlc.getInputStream(),
								urlc2.getInputStream(),80,80);

			} else if (flag == 2) {
				return Tools
						.decodeSampledBitmapFromStream(urlc.getInputStream(),
								urlc2.getInputStream(), 200, 200);
			}else if(flag==3){
				return Tools.decodeSampledBitmapFromStream(urlc.getInputStream(),
						urlc2.getInputStream(),800, 800);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap decodeSampledBitmapFromStream(InputStream is,
			InputStream is2, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		//Ҳ����˵��������ǰ�����Ϊtrue��
		//��ôBitmapFactory.decodeFile(String path, Options opt)��������ķ���һ��Bitmap���㣬
		//������������Ŀ���ȡ�������㣬�����Ͳ���ռ��̫����ڴ棬Ҳ�Ͳ�����ôƵ���ķ���OOM�ˡ�
		
		//�����ʱ������ֻ��ҪͼƬ(InputStream is)�Ŀ�͸ߣ�����ѹ��ͼƬ����������Ҫ����Bitmap������
		//options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, options);
		//����ѹ��ͼƬΪԭʼͼƬ��     1/inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,reqHeight);
		options.inJustDecodeBounds = false;
		//��ʱ����Ҫ��������Bitmap����options.inJustDecodeBounds = false;
//		Log.i("Bitmap", "options.inSampleSize: " + options.inSampleSize
//				+ "\noptions.outWidth:" + options.outWidth
//				+ " options.outHeight:" + options.outHeight);
		return BitmapFactory.decodeStream(is2, null, options);

	}
	public static Bitmap decodeSampledBitmapFromStream_large(InputStream is){
		return BitmapFactory.decodeStream(is);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		//ͼƬ��С
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
//		Log.i("Bitmap", "reqWidth" + reqWidth + "reqHeight" + reqHeight);

		if (height > reqHeight || width > reqWidth) {
			//����imageview�Ĵ�Сѹ��ͼƬ������OOM
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

//			Log.i("Bitmap", "heightRatio" + heightRatio + "widthRatio"
//					+ widthRatio);
			inSampleSize = heightRatio <= widthRatio ? heightRatio : widthRatio;
//			Log.i("Bitmap", "inSampleSize:" + inSampleSize);
		}
		//����ͼƬ��С��imageview��С����
		return inSampleSize;
	}
}
