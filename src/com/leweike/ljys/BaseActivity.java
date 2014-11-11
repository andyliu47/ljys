package com.leweike.ljys;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.leweike.ljys.utils.UploadMediaThread;

/**
 * 项目: ljys 描述: 创建日期: 2014-11-4 上午11:45:02
 * 
 * @author
 */
public class BaseActivity extends Activity {

	protected static final int REQUEST_CODE_ABLUM = 1;
	protected static final int REQUEST_CODE_CAMERA = 2;
	protected static final int REQUEST_CODE_CAMERA_HAS_NAME = 3;

	protected void fullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	protected void quitFullScreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	/**
	 * 读取相册内图片
	 */
	protected void callAblum() {
		Intent ablum = new Intent(Intent.ACTION_PICK);
		ablum.setType("image/*");
		startActivityForResult(ablum, REQUEST_CODE_ABLUM);
	}

	/**
	 * 调用相机，照片保存在默认位置，且使用系统命名
	 */
	protected void callCamera() {
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(camera, REQUEST_CODE_CAMERA);
	}

	/**
	 * 调用相机，传入文件名，图片保存在 /mnt/sdcard/Android/data/com.leweike/cache/image/ 下
	 * 
	 * @param fileName
	 */
	protected void callCamera(String fileName) {
		File filePath = new File(getExternalCacheDir().getPath() + "/image");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath, fileName + ".jpg")));
		startActivityForResult(camera, REQUEST_CODE_CAMERA_HAS_NAME);
	}

	protected Uri callbackAblumOrCamera(int requestCode, Intent data) {
		Uri uri = null;
		if (data != null) {
			if (requestCode == REQUEST_CODE_ABLUM) {
				uri = data.getData();
			} else if (requestCode == REQUEST_CODE_CAMERA) {
				uri = data.getData();
			} else if (requestCode == REQUEST_CODE_CAMERA_HAS_NAME) {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				BufferedOutputStream bos = null;
				try {
					File filePath = new File(getExternalCacheDir().getPath() + "/image");
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					File file = new File(filePath, System.currentTimeMillis() + ".jpg");
					if (file.exists()) {
						file.delete();
					}
					bos = new BufferedOutputStream(new FileOutputStream(file));
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					uri = Uri.fromFile(file);
				} catch (Exception e) {
					Log.e(this.toString(), "保存图片失败");
				} finally {
					try {
						bos.flush();
						bos.close();
					} catch (IOException e) {
						Log.e(this.toString(), "保存图片失败");
					}
				}
			}
		}
		new Thread(new UploadMediaThread(this, uri)).start();;

		return uri;
	}
}
