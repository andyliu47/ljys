package com.leweike.ljys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences spf = getSharedPreferences("logininfo", MODE_PRIVATE);
		int loginCount = spf.getInt("loginCount", 0);
		if (loginCount == 0) {

			setContentView(R.layout.main);

			Button loginButton = (Button) findViewById(R.id.main_login_btn);
			Button registerButton = (Button) findViewById(R.id.main_register_btn);
			TextView visitorTextView = (TextView) findViewById(R.id.main_visitor_btn);
			loginButton.setOnClickListener(listener);
			registerButton.setOnClickListener(listener);
			visitorTextView.setOnClickListener(listener);

		} else {
			String username = spf.getString("username", null);
			String password = spf.getString("password", null);
			if (username == null || password == null) {
				toLogin();
			} else {
				toHome(1);
			}

		}
		Editor editor = spf.edit();
		editor.putInt("loginCount", 1);
		editor.commit();

	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.main_login_btn:
				toLogin();
				break;
			case R.id.main_register_btn:
				toRegister();
				break;
			case R.id.main_visitor_btn:
				toHome(0);
				break;
			default:
				break;
			}
			MainActivity.this.finish();
		}
	};

	private void toLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private void toRegister() {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	private void toHome(int type) {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra("type", type);
		startActivity(intent);
		this.finish(); 
	}
}
