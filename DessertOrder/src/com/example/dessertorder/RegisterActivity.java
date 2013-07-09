package com.example.dessertorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
				if(checkNetworkState()){
					Intent intent =  new Intent();
					intent.setClass(RegisterActivity.this, FullscreenActivity.class); 
					startActivity(intent);
				}
				
			}
		});
	}
	public boolean checkNetworkState(){
		ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {//¼ì²éÍøÂçÊÇ·ñÁ¬½Ó  
            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
            if (info != null) {  
                for (int i = 0; i < info.length; i++) {  
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {  
                        return true;  
                    }  
                }  
            }  
        }
    	//ÉèÖÃÍøÂç	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.netstate);
		builder.setMessage(R.string.setnetwork);
		builder.setPositiveButton(R.string.setup, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent =null;
				if(android.os.Build.VERSION.SDK_INT>10){
					intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
				}else{
					intent= new Intent();
					 ComponentName comp = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
					 intent.setComponent(comp);
					 intent.setAction("android.intent.action.VIEW");
				}
				startActivity(intent);
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
		return false;
	}
}
