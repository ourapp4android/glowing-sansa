package com.example.dessertorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	private Button loginBtn;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		loginBtn= (Button)findViewById(R.id.login_btn_login);
		loginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//µÇÂ¼Ìø×ª
				Intent intent =  new Intent();
				intent.setClass(LoginActivity.this, FullscreenActivity.class);
				startActivity(intent);
			}
		});
	}
}
