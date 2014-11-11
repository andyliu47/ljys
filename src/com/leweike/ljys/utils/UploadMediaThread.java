package com.leweike.ljys.utils;

import java.io.File;

import org.json.JSONObject;

import com.qiniu.android.http.ResponseInfo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class UploadMediaThread implements Runnable {

	private Context context;
	private Uri uri;
	
	
	public UploadMediaThread(Context context, Uri uri) {
		super();
		this.context = context;
		this.uri = uri;
	}


	@Override
	public void run() {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = context.getContentResolver().query(uri, proj, null, null, null);
		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor.getString(actual_image_column_index);
		File file1 = new File(img_path);
		QiniuUploadUtils.upload(context,1 , file1, new UploadCallback() {
			
			@Override
			public void callback(String key, ResponseInfo info, JSONObject response) {
				
			}
		});
	}

}
