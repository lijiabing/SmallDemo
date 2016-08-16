package com.robot.autorobot.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import com.google.gson.Gson;
import com.robot.autorobot.chatmessage.ChatMessage;
import com.robot.autorobot.chatmessage.ChatMessage.Type;
import com.robot.autorobot.chatmessage.Result;


public class HttpUtils {
	//接口地址
	private final static String ADDRESS="http://www.tuling123.com/openapi/api";
	private final static String API_KEY="c15e1ef84bc1fec49053300e23e69998";
	
	
	public static ChatMessage sendChatMsg(String msg){
		ChatMessage chatmsg=new ChatMessage();
		String result=doPost(msg);
		Gson gson=new Gson();
		Result res=null;
		try {
			res=gson.fromJson(result, Result.class);
			chatmsg.setMsg(res.getText());
		} catch (Exception e) {
			chatmsg.setMsg("服务器繁忙，请稍后再试！");
		}
		
		chatmsg.setType(Type.IN);
		chatmsg.setDate(new Date());
		return chatmsg;
		
	}
	
	public static String doPost(String msg){
		
		String result="";
		String holeUrl=getUrl(msg);
		URL url=null;
		HttpURLConnection connect=null;
		 InputStream is=null;
		 ByteArrayOutputStream baos=null;
		try {
			url = new URL(holeUrl);
			connect=(HttpURLConnection) url.openConnection(); 
		    connect.setConnectTimeout(8*1000);
		    connect.setReadTimeout(8*1000);
		    connect.setRequestMethod("GET");
		    is=connect.getInputStream();
		    baos=new ByteArrayOutputStream();
			
		    int len=-1;
		    byte[] buf=new byte[128];
		    
		    while((len=is.read(buf))!=-1){
		    	baos.write(buf, 0, len);
		    }
		    baos.flush();
		    result=new String(baos.toByteArray());
		    
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		
		
		
		return result;
	}

	private static String getUrl(String msg) {
		String url=null;
		try {
			url=ADDRESS+"?key="+API_KEY+"&info="+URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return url;
	}
	
	
	
	
}
