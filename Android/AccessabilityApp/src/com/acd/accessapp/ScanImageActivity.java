/**
 * 
 */
package com.acd.accessapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author C-Hall
 *
 */
public class ScanImageActivity extends Activity implements OnClickListener
{
	
	ImageView camImage = null;
	Button btnScan = null;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanimage);
		
		camImage = (ImageView) findViewById(R.id.imageView_scan);
		btnScan = (Button) findViewById(R.id.btn_scan);
		
		btnScan.setOnClickListener(this);
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		Intent iOpenCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(iOpenCamera, 1);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode==1)
		{
			Bitmap bm = (Bitmap)data.getExtras().get("data");
			camImage.setImageBitmap(bm);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
