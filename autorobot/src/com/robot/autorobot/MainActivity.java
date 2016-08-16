package com.robot.autorobot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.robot.autorobot.chatmessage.ChatMessage;
import com.robot.autorobot.chatmessage.ChatMessage.Type;
import com.robot.autorobot.chatmessage.ChatMessageAdapter;
import com.robot.autorobot.util.HttpUtils;

public class MainActivity extends Activity {

	private List<ChatMessage> mSource;//数据源
	private ListView mlvMsg;//listview
	private Button btnSend;//发送按钮
	private EditText edInput;//编辑框
	private ChatMessageAdapter msgAdapter;//消息适配器
	private final String TAG="这是一个tag";
	private Handler handler=new Handler(){//更新主线程的界面，将消息添加在listview 的数据集中

		@Override
		public void handleMessage(Message msg) {
			ChatMessage chatmsg=(ChatMessage) msg.obj;
			mSource.add(chatmsg);
			msgAdapter.notifyDataSetChanged();
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initLitener();
		
		
	}

	private void initLitener() {
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Log.i(TAG, "**************************");
				final String toMsg=edInput.getText().toString();
				ChatMessage toChatMsg=new ChatMessage();
				toChatMsg.setDate(new Date());
				toChatMsg.setType(Type.OUT);
				toChatMsg.setMsg(toMsg);
				mSource.add(toChatMsg);
				msgAdapter.notifyDataSetChanged();
				edInput.setText("");
				new Thread(){

					@Override
					public void run() {
						ChatMessage fromMsg=HttpUtils.sendChatMsg(toMsg);
						Message m=Message.obtain();
						m.obj=fromMsg;
						handler.sendMessage(m);
					}
					
				}.start();

				
			}
		});
		
	}

	private void initData() {
		mSource=new ArrayList<ChatMessage>();
		mSource.add(new ChatMessage("你好！欢迎你的到来！",Type.IN,new Date()));
		msgAdapter=new ChatMessageAdapter(this, mSource);
		mlvMsg.setAdapter(msgAdapter);
	}

	private void initView() {
		mlvMsg=(ListView) findViewById(R.id.id_list_msg);
	    btnSend=(Button) findViewById(R.id.id_btn_send_msg);
		edInput=(EditText) findViewById(R.id.id_input);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
