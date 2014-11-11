package com.leweike.ljys.utils;


import java.io.File;
import java.text.MessageFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

/**
 * 该类请不要在主线程调用
 * @author yym
 *
 */
public class QiniuUploadUtils {
	private static UploadManager uploadManager;
	private static final String UPLOAD_TOKEN = "uploadTokenFile";
	private static final String IMAGE_UPLOAD_TOKEN = "imageUploadToken";
	private static final String IMAGE_UPLOAD_TOKEN_EXPIRES = "imageUploadTokenExpires";
	private static final String MEDIA_UPLOAD_TOKEN = "mediaUploadToken";
	private static final String MEDIA_UPLOAD_TOKEN_EXPIRES = "mediaUploadTokenExpires";
	private static final String VIDEO_UPLOAD_TOKEN = "videoUploadToken";
	private static final String VIDEO_UPLOAD_TOKEN_EXPIRES = "videoUploadTokenExpires";
	
	static{
		uploadManager = new UploadManager();
	}
	
	private static String getToken(Context context,int type){
		SharedPreferences spf = context.getSharedPreferences(UPLOAD_TOKEN, 0);
		String uploadToken = null;
		long uploadTokenExpires = 0;
		long currentTimeMillis = System.currentTimeMillis();
		if (type == 1) {
			uploadTokenExpires = spf.getLong(IMAGE_UPLOAD_TOKEN_EXPIRES, 0);
			if (uploadTokenExpires > currentTimeMillis) {
				uploadToken = spf.getString(IMAGE_UPLOAD_TOKEN, null);
			}else {
				uploadToken = getTokenFromHttp(context, type);
			}
		}else if(type == 2) {
			uploadTokenExpires = spf.getLong(MEDIA_UPLOAD_TOKEN_EXPIRES, 0);
			if (uploadTokenExpires > currentTimeMillis) {
				uploadToken = spf.getString(MEDIA_UPLOAD_TOKEN, null);
			}else {
				uploadToken = getTokenFromHttp(context, type);
			}
		}else if(type == 3) {
			uploadTokenExpires = spf.getLong(VIDEO_UPLOAD_TOKEN_EXPIRES, 0);
			if (uploadTokenExpires > currentTimeMillis) {
				uploadToken = spf.getString(VIDEO_UPLOAD_TOKEN, null);
			}else {
				uploadToken = getTokenFromHttp(context, type);
			}
		}
		return uploadToken;
	}
	
	private static String getTokenFromHttp(Context context,int type){
		String url = PropertyUtils.get(context, "get.upload.token");
		String response = HttpUtils.getRequest(MessageFormat.format(url, type), null);
		String uploadToken = null;
		long uploadTokenExpires = 0;
		if (response != null) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				if (jsonObject.has("errorCode") && jsonObject.getInt("errorCode") == 0) {
						uploadToken = jsonObject.getString("token");
						uploadTokenExpires = jsonObject.getLong("expires");
				};
			} catch (JSONException e) {
				Log.e(context.toString(), "获取token失败");
			}
		}
		if (uploadToken != null && uploadTokenExpires != 0) {
			SharedPreferences spf = context.getSharedPreferences(UPLOAD_TOKEN, 0);
			Editor editor = spf.edit();
			if (type == 1) {
				editor.putString(IMAGE_UPLOAD_TOKEN, uploadToken);
				editor.putLong(IMAGE_UPLOAD_TOKEN_EXPIRES, uploadTokenExpires);
			}else if (type == 2) {
				editor.putString(MEDIA_UPLOAD_TOKEN, uploadToken);
				editor.putLong(MEDIA_UPLOAD_TOKEN_EXPIRES, uploadTokenExpires);
			}else if (type == 3) {
				editor.putString(VIDEO_UPLOAD_TOKEN, uploadToken);
				editor.putLong(VIDEO_UPLOAD_TOKEN_EXPIRES, uploadTokenExpires);
			}
			editor.commit();
		}
		return uploadToken;
	}
	
	/**
	 * @param context
	 * @param type 文件类型 1 image 2 media 3 video
	 * @param file
	 * @param key
	 * @param callback
	 */
	public static void upload(Context context,int type,File file,final UploadCallback callback){
		uploadManager.put(file, file.getName(), getToken(context,type), new UpCompletionHandler(){

			@Override
			public void complete(String key, ResponseInfo info, JSONObject response) {
				if (callback != null) {
					callback.callback(key, info, response);
				}
			}
			
		}, null);
	}
	
	/*public static void upload(Context context,byte[] bytes,String key,final UploadCallback callback){
		uploadManager.put(bytes, key, getToken(context), new UpCompletionHandler(){

			@Override
			public void complete(String key, ResponseInfo info, JSONObject response) {
				if (callback != null) {
					callback.callback(key, info, response);
				}
			}
			
		}, null);
	}
	public static void upload(Context context,String filePath,String key,final UploadCallback callback){
		uploadManager.put(filePath, key, getToken(context), new UpCompletionHandler(){

			@Override
			public void complete(String key, ResponseInfo info, JSONObject response) {
				if (callback != null) {
					callback.callback(key, info, response);
				}
			}
			
		}, null);
	}*/
}
