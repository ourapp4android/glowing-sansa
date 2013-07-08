package com.example.dessertorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class RegisterActivity extends Activity {
	private Button loginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_page);
		loginBtn =(Button)findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//µÇÂ¼Ìø×ª
				Intent intent =  new Intent();
				intent.setClass(RegisterActivity.this, FullscreenActivity.class); 
				startActivity(intent);
			}
		});
	}
	
}
