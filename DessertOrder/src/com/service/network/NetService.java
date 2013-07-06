package com.service.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class NetService extends Service {
	
	private static final String TAG="Message.NetService"; 
    private static final int MULTICAST_PORT=5111; 
    private static final String GROUP_IP="224.5.0.7";    
    private static Set<String> clientList = new HashSet<String>();
    
    private Message sendMsg = null;
   
    private Messenger receiveMessenger;
	
    private MulticastLock multicastLock;
    
    
    private ServiceBinder serviceBinder = new ServiceBinder();
    
    public class ServiceBinder extends Binder implements ICommunicateService{

		@Override
		public void setReceiveMessenger(Messenger recevie) {
			receiveMessenger  = recevie;
		}

		

		@Override
		public void sendMessge(Message msg) {
			sendMsg = msg;
			new Thread(sendRunnable).start();
			
		}



		@Override
		public void startReceiver() {
			new Thread(receiveRunnable).start();
		}
    	
    }

	@Override
	public void onCreate() {
		Log.d(TAG,">>>service start");
		super.onCreate();
		WifiManager wifiMgr = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		multicastLock = wifiMgr.createMulticastLock("multicast.test");
		multicastLock.acquire();
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG,">>>service destory");
		multicastLock.release();
		super.onDestroy();
		
	}
	
	private Runnable sendRunnable =new Runnable(){
		private MulticastSocket  multicastSocket;
		private InetAddress group;
		
		public void run(){
			
			try {
				multicastSocket =new MulticastSocket(MULTICAST_PORT);
				group = InetAddress.getByName(GROUP_IP);
		    	multicastSocket.joinGroup(group);
		    	multicastSocket.setTimeToLive(1);
		    	multicastSocket.setLoopbackMode(true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.d("Send Thread",">>>start send msg");
			String data = (String)sendMsg.obj;
			DatagramPacket packet=new DatagramPacket(data.getBytes(), data.length(),group,MULTICAST_PORT);
	    	try {
				multicastSocket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	Log.d("Send Thread",">>>send packet ok");
		
		}
	};
	Runnable receiveRunnable = new Runnable(){
		private MulticastSocket  multicastSocket;
		private InetAddress group;
		public void run(){
			try {
				multicastSocket =new MulticastSocket(MULTICAST_PORT);
				group = InetAddress.getByName(GROUP_IP);
		    	multicastSocket.joinGroup(group);
		    	multicastSocket.setTimeToLive(1);
		    	multicastSocket.setLoopbackMode(true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			while(true){
		     	byte[] receiveData=new byte[100]; 
		    	DatagramPacket packet=new DatagramPacket(receiveData, receiveData.length); 
		        try {
		        	//
		        	 Log.d(TAG,">>>started receive msg");
					multicastSocket.receive(packet);
					String packetIpAddress=packet.getAddress().getHostAddress();
					//
		            Log.d(TAG,">>>packet ip address: "+packetIpAddress);
		            clientList.add(packetIpAddress);
		            //
		            
		            StringBuilder packetContent=new StringBuilder();
		            for(int i=0;i<receiveData.length;i++){
		            	packetContent.append((char)receiveData[i]);
		            }
		            MsgEntity entity = new MsgEntity();
		            entity.setComeMsg(true);
		            entity.setContent(packetContent.toString());
		            entity.setUserName(packetIpAddress);
		            Message msg = new Message();
		            msg.what=2;
		            msg.obj = entity;
		            try {
		            	if(receiveMessenger!=null){
		            		receiveMessenger.send(msg);
		            	}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		            
				} catch (IOException e) {
					e.printStackTrace();
				}
		    
			}
		
		}
	};
	
	@Override
	public IBinder onBind(Intent arg0) {
		return serviceBinder;
	}

}
