package com.leweike.ljys.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class PropertyUtils {
	private static Properties properties;
	
	public static String get(Context context,String key) {
		if (properties == null) {
			InputStream in = null;
			try {
				in = context.getAssets().open("config.properties");
				properties = new Properties();
				properties.load(in);
			} catch (IOException e) {
				Log.e(context.toString(), "读取配置文件失败");
			}
		}
		return properties.getProperty(key);
	}
}
