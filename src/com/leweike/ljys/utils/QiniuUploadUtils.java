package com.leweike.ljys.utils;


import java.io.File;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

public class QiniuUploadUtils {
	private static UploadManager uploadManager;
	private static final String UPLOAD_TOKEN_FILE = "uploadTokenFile";
	private static final String UPLOAD_TOKEN = "uploadToken";
	private static final String UPLOAD_TOKEN_EXPIRES = "uploadTokenExpires";
	
	static{
		uploadManager = new UploadManager();
	}
	
	static String getToken(Context context){
		SharedPreferences spf = context.getSharedPreferences(UPLOAD_TOKEN_FILE, 0);
		long uploadTokenExpires = spf.getLong(UPLOAD_TOKEN_EXPIRES, 0);
		String uploadToken = null;
		if (uploadTokenExpires > System.currentTimeMillis()) {
			uploadToken = spf.getString(UPLOAD_TOKEN, null);
		}else {
			//TODO
		}
		return uploadToken;
	}
	
	public static void upload(Context context,byte[] bytes,String key,final UploadCallback callback){
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
	}
	public static void upload(Context context,File file,String key,final UploadCallback callback){
		uploadManager.put(file, key, getToken(context), new UpCompletionHandler(){

			@Override
			public void complete(String key, ResponseInfo info, JSONObject response) {
				if (callback != null) {
					callback.callback(key, info, response);
				}
			}
			
		}, null);
	}
}
