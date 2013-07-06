package com.service.network;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dessertorder.R;

public class ChatAdapter extends BaseAdapter {
	
	private Context context = null;  
    private List<MsgEntity> chatList = null;  
    private LayoutInflater inflater = null;  
    private int COME_MSG = 0;  
    private int TO_MSG = 1;
	private ChatHolder chatHolder = null;
    
    public ChatAdapter(Context context,List<MsgEntity> chatList){
   	 this.context = context;
   	 this.chatList = chatList;
   	 inflater = LayoutInflater.from(this.context);
    }
    
	@Override
	public int getCount() {
		return chatList.size();
	}

	@Override
	public Object getItem(int position) {
		return chatList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getItemViewType(int position) {
		MsgEntity entity = chatList.get(position);
		if(entity.isComeMsg()){
			return COME_MSG;
		}else{
			return TO_MSG;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		//两种风格
		return 2;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView ==null){
			chatHolder = new ChatHolder();
			if(chatList.get(position).isComeMsg()){
				convertView = inflater.inflate(R.layout.recevie_msg_item, null);
			}else{
				convertView = inflater.inflate(R.layout.send_msg_item, null);
			}
			chatHolder.timeView = (TextView)convertView.findViewById(R.id.tv_time);
			chatHolder.contentView = (TextView)convertView.findViewById(R.id.tv_content);
			chatHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_user_image);
		}
		MsgEntity entity = chatList.get(position);
		chatHolder.timeView.setText(entity.getUserName());
		chatHolder.contentView.setText(entity.getContent());
		chatHolder.imageView.setImageResource(R.drawable.comment);
		return convertView;
		
	}
	public class ChatHolder {
		public TextView timeView;
		public ImageView imageView;
		public TextView contentView;
	}
}
