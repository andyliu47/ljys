package com.leweike.ljys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 项目: ljys 描述: 创建日期: 2014-11-3 下午5:01:13
 * 
 * @author
 */
public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText usernameEditText;
	private EditText passwordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginButton = (Button) findViewById(R.id.login_btn);
		usernameEditText = (EditText) findViewById(R.id.login_username);
		passwordEditText = (EditText) findViewById(R.id.login_password);
		loginButton.setOnClickListener(listener);
	}

	private OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			SharedPreferences spf = getSharedPreferences("logininfo", MODE_PRIVATE);
			Editor editor = spf.edit();
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();

			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();

		}
	};
}
