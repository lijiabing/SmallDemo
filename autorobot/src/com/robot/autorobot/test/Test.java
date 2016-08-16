package com.robot.autorobot.test;

import com.robot.autorobot.util.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase {

	private final String TAG="这是测试的返回结果！";
	public void doTestInfo()
	{
		
		String str=HttpUtils.doPost("给我讲个笑话");
		Log.e(TAG, str);
		
	}
	
}
