package com.robot.autorobot.test;

import com.robot.autorobot.util.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase {

	private final String TAG="���ǲ��Եķ��ؽ����";
	public void doTestInfo()
	{
		
		String str=HttpUtils.doPost("���ҽ���Ц��");
		Log.e(TAG, str);
		
	}
	
}
