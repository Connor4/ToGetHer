package com.running.together.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * �첽����ͼƬ����
 * 
 * @author hanfei.li
 * 
 */
public class AsyncImageLoader {
	/**
	 * ͼƬ���ݻ��� key = url ,value = ͼƬ��Դ����
	 * ʹ�������û���ͼƬ
	 */
	private static HashMap<String, SoftReference<Bitmap>> imageCache;

	static {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * �첽����ͼƬ
	 * 
	 * @param url
	 *            ͼƬ�ĵ�ַ
	 * @param imageView
	 *            ��Ҫ��ʾͼƬ�����
	 * @param callback
	 *            �ص��ӿ�
	 * @return ͼƬ��Դ
	 */
	public static Bitmap loadBitmap(final int flag, final String url,
			final ImageView imageView, final int position,
			final ImageCallback callback) {
		// �ж��Ƿ��Ѿ����ع���������ع���ֱ�ӻ�ò�����
		if (imageCache.containsKey(url)) {
			SoftReference<Bitmap> soft = imageCache.get(url);
//			Log.i("ImageCache000", url.toString() + "\nPosition:" + position);
			Bitmap bitmap = soft.get();
			// Drawable dra = imageCache.get(url);
			if (bitmap != null) {
				return bitmap;
			} else {
//				Log.i("ImageCache111", url.toString() + "\nPosition:"
//						+ position);
			}
		}

//		final Handler handler = new Handler() {
//			@Override
//			public synchronized void handleMessage(Message msg) {
//				// ͼƬ��Դ���ò���
//				if (imageView != null && !url.equals("")) {
//					callback.imageSet((Bitmap) msg.obj, imageView);
////					Log.i("!!", " handleMessage" + url + "position : "
////							+ position);
//				}
//
//			}
//		};
		// ���ز���
		new Thread() {
			public void run() {
				Bitmap bitmap = Tools.getBitmapFromUrl(flag, url);
				// ���û��棬�����ظ�������ͬ��ͼƬ��Դ
				if(position!=10086){
//					Log.i("imagecache", "!10086");
					imageCache.put(url, new SoftReference<Bitmap>(bitmap));
				}
				/*
				 * synchronized (handler) {
				 * 
				 * }
				 */
//				Message msg = handler.obtainMessage(0, bitmap);
//				handler.sendMessage(msg);

//				Log.i("ImageCache222", url.toString() + "\nPosition:"
//						+ position);
			}
		}.start();
		return null;
	}

	/**
	 * �ص��ӿ�
	 * 
	 * @author 
	 * 
	 */
	public interface ImageCallback {
		/**
		 * ͼƬ��Դ����
		 * 
		 * @param bitmap
		 * @param iv
		 */
		public void imageSet(Bitmap bitmap, ImageView iv);

	}

}
