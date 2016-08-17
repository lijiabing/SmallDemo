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
 * 利用RenderScript实现高斯模糊效果
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
	 * 高斯模糊的实现 SDK Build-tools 必须大于或等于18
	 * 			  SDK Tools 必须小于或等于22
	 * @param bmp  传入的要实现模糊的Bimap图片 ，可以用BitmapFactory.decodeResource（）获取
	 * @param img  传入的ImageView
	 */
	private void bulr(Bitmap bmp, ImageView img) {
		 	RenderScript rs = RenderScript.create(context);// 构建一个RenderScript对象
		    Allocation overlayAlloc = Allocation.createFromBitmap(rs, bmp);// 创建用于输入的脚本类型
		    ScriptIntrinsicBlur blur = 
		        ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());// 创建高斯模糊脚本
		    blur.setInput(overlayAlloc);// 设置输入脚本类型
		    blur.setRadius(radius);// 设置模糊半径，范围0f<radius<=25f
		    blur.forEach(overlayAlloc);// 执行高斯模糊算法，并将结果填入输出脚本类型中
		    overlayAlloc.copyTo(bmp);// 将输出内存编码为Bitmap，图片大小必须注意
		    img.setBackground(new BitmapDrawable(getResources(), bmp));//设置背景
		    rs.destroy();// 关闭RenderScript对象
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
