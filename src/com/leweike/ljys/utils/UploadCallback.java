package com.leweike.ljys.utils;

import org.json.JSONObject;

import com.qiniu.android.http.ResponseInfo;

public interface UploadCallback {
	void callback(String key, ResponseInfo info, JSONObject response);
}
