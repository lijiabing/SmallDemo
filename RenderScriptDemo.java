package com.example.bulrdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
/**
 * ����RenderScriptʵ�ָ�˹ģ��Ч��
 * @author Jackbing
 *
 */
public class RenderScriptDemo extends Activity {

	 final float radius=10.f;
	 private Context context=null;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context=getApplicationContext();
		final ImageView img=(ImageView) findViewById(R.id.blur_img);
		final Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.beautygirl);
		bulr(bmp,img);
		//img.setBackground(new BitmapDrawable(getResources(), bmp));
	}

	/**
	 * ��˹ģ����ʵ�� SDK Build-tools ������ڻ����18
	 * 			  SDK Tools ����С�ڻ����22
	 * @param bmp  �����Ҫʵ��ģ����BimapͼƬ ��������BitmapFactory.decodeResource������ȡ
	 * @param img  �����ImageView
	 */
	private void bulr(Bitmap bmp, ImageView img) {
		 	RenderScript rs = RenderScript.create(context);// ����һ��RenderScript����
		    Allocation overlayAlloc = Allocation.createFromBitmap(rs, bmp);// ������������Ľű�����
		    ScriptIntrinsicBlur blur = 
		        ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());// ������˹ģ���ű�
		    blur.setInput(overlayAlloc);// ��������ű�����
		    blur.setRadius(radius);// ����ģ���뾶����Χ0f<radius<=25f
		    blur.forEach(overlayAlloc);// ִ�и�˹ģ���㷨�����������������ű�������
		    overlayAlloc.copyTo(bmp);// ������ڴ����ΪBitmap��ͼƬ��С����ע��
		    img.setBackground(new BitmapDrawable(getResources(), bmp));//���ñ���
		    rs.destroy();// �ر�RenderScript����
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
	
	
	
//	public Bitmap bulr(Bitmap sentBitmap){
//		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
//
//        final RenderScript rs = RenderScript.create(context);
//        final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
//                Allocation.USAGE_SCRIPT);
//        final Allocation output = Allocation.createTyped(rs, input.getType());
//        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        script.setRadius(radius /* e.g. 3.f */);
//        script.setInput(input);
//        script.forEach(output);
//        output.copyTo(bitmap);
//        return bitmap;
//	}

	
}
