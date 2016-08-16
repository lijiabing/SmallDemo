package com.robot.autorobot.chatmessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.robot.autorobot.R;
import com.robot.autorobot.chatmessage.ChatMessage.Type;

public class ChatMessageAdapter extends BaseAdapter {

	private List<ChatMessage> mSource;
	private Context context;
	private LayoutInflater inflater;
	public ChatMessageAdapter(Context context,List<ChatMessage> mSource){
		this.context=context;
		this.mSource=mSource;
		inflater=LayoutInflater.from(context);
	}
	
	//消息个数
	public int getCount() {
		return mSource.size();
	}

	//返回一个item对象，即消息对象
	public Object getItem(int position) {
		
		return mSource.get(position);
	}

	
	//返回item的位置
	public long getItemId(int position) {
		return position;
	}
	
	

	//如果消息是发送过来的则返回0，反之则返回1
	@Override
	public int getItemViewType(int position) {
		if(mSource.get(position).getType()==Type.IN){
			return 0;
		}
		
		return 1;
	}
	
	//一共有两种类型
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	//根据ItemType的不同类型来设置不同的布局
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage chatmsg=mSource.get(position);
		ViewHolder viewholder=null;
		if(convertView==null){
			if(getItemViewType(position)==0){
				convertView=inflater.inflate(R.layout.from_msg_layout, parent,false);
				viewholder=new ViewHolder();
				viewholder.date=(TextView) convertView.findViewById(R.id.id_from_date);
				viewholder.msg=(TextView) convertView.findViewById(R.id.id_from_msg_info);
			}else{
				convertView=inflater.inflate(R.layout.to_msg_layout, parent,false);
				viewholder=new ViewHolder();
				viewholder.date=(TextView) convertView.findViewById(R.id.id_to_date);
				viewholder.msg=(TextView) convertView.findViewById(R.id.id_to_msg_info);
			}
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewholder.date.setText(df.format(chatmsg.getDate()));
		viewholder.msg.setText(chatmsg.getMsg());
		
		return convertView;
	}
	
	
	private final class ViewHolder 
	{
		TextView date;
		TextView msg;
	}

}
