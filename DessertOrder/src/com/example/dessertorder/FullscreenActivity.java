package com.example.dessertorder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dessertorder.util.SystemUiHider;
import com.service.network.ChatAdapter;
import com.service.network.ICommunicateService;
import com.service.network.MsgEntity;
import com.service.network.NetService;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
	
	private Button sendButton = null;  
    private EditText contentEditText = null;  
    private ListView chatListView = null;  
    private List<MsgEntity> chatList = null;  
    private ChatAdapter chatAdapter = null; 
    
    private static String TAG ="my activity";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen); 

		
		
		
		sendButton = (Button)findViewById(R.id.dummy_button);
		contentEditText = (EditText)findViewById(R.id.editText1);
		chatListView = (ListView)findViewById(R.id.listView1);
		
		chatList = new ArrayList<MsgEntity>();
		chatAdapter = new ChatAdapter(this, chatList);
		
		chatListView.setAdapter(chatAdapter);
		chatListView.setSelection(chatListView.getBottom());
		contentEditText.clearFocus();
		
		//
		Intent serviceIntent = new Intent(this,NetService.class);
		bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
		sendButton.setOnClickListener(new OnClickListener() {
			//发送广播消息
			@Override
			public void onClick(View v) {
				String inputText = contentEditText.getText().toString();
				if(!inputText.equals("")){
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = inputText;
					if(netService!=null){
						Log.d(TAG,">>>click send start");
						//
						MsgEntity entity = new MsgEntity();
						entity.setComeMsg(false);
						entity.setContent(inputText);
						entity.setUserName("me");
						chatList.add(entity);
						chatAdapter.notifyDataSetChanged();
						
						//
						contentEditText.setText("");
						//
						netService.sendMessge(msg);
						
					}
				}else{
					Toast.makeText(FullscreenActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}
	Handler msgHandler = new Handler(){
		public void handleMessage(Message msg){
			Log.d(TAG,">>>show msg start");
			chatList.add((MsgEntity)msg.obj);
			chatAdapter.notifyDataSetChanged();
			chatListView.setSelection(chatList.size()-1);
			Log.d(TAG,">>>show msg finish ");
		}
	};
	
	final Messenger mMessenger = new Messenger(msgHandler);
	private ICommunicateService netService;
	private ServiceConnection mConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) {
			Log.d(TAG,">>>onServiceConnected  start");
			netService =(ICommunicateService)service;
			netService.setReceiveMessenger(mMessenger);
			netService.startReceiver();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			netService = null;
		}
		
	};
	
	
	protected void onDestroy() {
		this.unbindService(mConnection);
		super.onDestroy();
	};

	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	
	
	
	
}
