package com.leweike.ljys;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 项目: ljys
 * 描述: 
 * 创建日期: 2014-11-4 上午11:45:02 
 * @author
 */
public class BaseActivity extends Activity {
	
	
	protected void fullScreen(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}
	protected void quitFullScreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~ WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	
}
