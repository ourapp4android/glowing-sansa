package com.service.network;

import android.os.Message;
import android.os.Messenger;

public interface ICommunicateService {
	public void startReceiver();
	public  void setReceiveMessenger(Messenger recevieMessenger);
	public  void sendMessge(Message msg);
}
