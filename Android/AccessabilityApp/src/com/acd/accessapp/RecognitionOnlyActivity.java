// com.catchoom.catchoomexamples is free software. You may use it under the MIT license, which is copied
// below and available at http://opensource.org/licenses/MIT
//
// Copyright (c) 2014 Catchoom Technologies S.L.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
// Software, and to permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
// PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
// FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
// DEALINGS IN THE SOFTWARE.

package com.acd.accessapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.acd.accessapp.utils.UploadToServer;
import com.catchoom.CatchoomActivity;
import com.catchoom.CatchoomCamera;
import com.catchoom.CatchoomCameraView;
import com.catchoom.CatchoomCloudRecognition;
import com.catchoom.CatchoomCloudRecognitionError;
import com.catchoom.CatchoomCloudRecognitionItem;
import com.catchoom.CatchoomImage;
import com.catchoom.CatchoomImageHandler;
import com.catchoom.CatchoomResponseHandler;
import com.catchoom.CatchoomSDK;

public class RecognitionOnlyActivity extends CatchoomActivity implements CatchoomResponseHandler,CatchoomImageHandler, OnClickListener {

	private final String TAG = "CatchoomTrackingExample";
	private final static String COLLECTION_TOKEN="1c1bda4f97a6449b";

	String resultUrl = null;
	private View mScanningLayout;
	private View mTapToScanLayout;

	private TextView textUrl = null;

	CatchoomCamera mCamera;

	CatchoomCloudRecognition mCloudRecognition;

	LocationManager locationManager = null;
	double latitude = 0;
	double longitude = 0;
	
	File f = null;

	CatchoomCameraView cameraView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onPostCreate()
	{

		View mainLayout= (View) getLayoutInflater().inflate(R.layout.activity_recognition_only, null);
		cameraView = (CatchoomCameraView) mainLayout.findViewById(R.id.camera_preview);
		super.setCameraView(cameraView);
		setContentView(mainLayout);

		mScanningLayout = findViewById(R.id.layout_scanning);
		mTapToScanLayout = findViewById(R.id.tap_to_scan);
		textUrl = (TextView) findViewById(R.id.txtUrl);

		mTapToScanLayout.setClickable(true);
		mTapToScanLayout.setOnClickListener(this);


		//Initialize the SDK. From this SDK, you will be able to retrieve the necessary modules to use the SDK (camera, tracking, and cloud-recgnition)
		CatchoomSDK.init(getApplicationContext(),this);

		//Get the camera to be able to do single-shot (if you just use finder-mode, this is not necessary)
		mCamera= CatchoomSDK.getCamera();
		mCamera.setImageHandler(this); //Tell the camera who will receive the image after takePicture()

		//Setup cloud recognition
		mCloudRecognition= CatchoomSDK.getCloudRecognition();//Obtain the cloud recognition module
		mCloudRecognition.setResponseHandler(this); //Tell the cloud recognition who will receive the responses from the cloud
		mCloudRecognition.setCollectionToken(COLLECTION_TOKEN); //Tell the cloud-recognition which token to use from the finder mode


		mCloudRecognition.connect(COLLECTION_TOKEN);

	}

	@Override
	public void searchCompleted(ArrayList<CatchoomCloudRecognitionItem> results) {
		mScanningLayout.setVisibility(View.GONE);
		if(results.size()==0){
			Log.d(TAG,"Nothing found");
		}else{
			final CatchoomCloudRecognitionItem item = results.get(0);
			if (!item.isAR())
			{
				mTapToScanLayout.setVisibility(View.VISIBLE);
				mCamera.restartCameraPreview();
				textUrl.setText("Tap Here: \n "+item.getUrl());
				textUrl.setTextColor(Color.WHITE);
				textUrl.setTextSize(15);
				textUrl.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v) 
					{
						Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
						startActivity(launchBrowser);
					}
				});

				return;
			}
		}
		Toast.makeText(getBaseContext(),getString(R.string.recognition_only_toast_nothing_found), Toast.LENGTH_SHORT).show();
		mTapToScanLayout.setVisibility(View.VISIBLE);
		mCamera.restartCameraPreview();
	}

	/**
	 * 
	 */


	@Override
	public void connectCompleted(){
		Log.i(TAG,"Collection token is valid");
	}

	@Override
	public void requestFailedResponse(int requestCode,
			CatchoomCloudRecognitionError responseError) {
		Log.d(TAG,"requestFailedResponse");	
		Toast.makeText(getBaseContext(),getString(R.string.recognition_only_toast_nothing_found), Toast.LENGTH_SHORT).show();
		mScanningLayout.setVisibility(View.GONE);
		mTapToScanLayout.setVisibility(View.VISIBLE);
		mCamera.restartCameraPreview();

	}

	//Callback received for SINGLE-SHOT only (after takePicture).
	@Override
	public void requestImageReceived(CatchoomImage image)
	{
		
//		startActivity(new Intent(RecognitionOnlyActivity.this, MainActivityVedio.class));
		
		Bitmap bitmap = image.toBitmap();
		
		Bitmap bitmapone = getBitmapFromAsset("accendo_logo.png");
		
		/*if (bitmap.getWidth() == bitmapone.getWidth() && bitmap.getHeight() == bitmapone.getHeight())
		{*/
	        int[] pixels1 = new int[bitmap.getWidth() * bitmap.getHeight()];
	        int[] pixels2 = new int[bitmapone.getWidth() * bitmapone.getHeight()];
	        bitmap.getPixels(pixels1, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
	        bitmapone.getPixels(pixels2, 0, bitmapone.getWidth(), 0, 0, bitmapone.getWidth(), bitmapone.getHeight());
	        if (!Arrays.equals(pixels1, pixels2)) 
	        {
	           resultUrl="http://www.accendotechnologies.com/";
	           startActivity(new Intent(RecognitionOnlyActivity.this, VideoActivity.class));
	        }
	        else 
	        {
	        	resultUrl="";
	        }
//	    } else
//	    {
//	    	resultUrl="";
//	    }
//		
		
	
		
//		String imageName = image.toString();
//		String[] splitName = imageName.split("@", imageName.length());
//		Toast.makeText(getApplicationContext(), "Image Name is  "+splitName[1], Toast.LENGTH_LONG).show();
//		f = new File(Environment.getExternalStorageDirectory()+"/"+splitName[1]+".jpg");
//		try
//		{
//			FileOutputStream fout = new FileOutputStream(f);
//			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout); 
//			fout.flush();
//			fout.close();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		ImageAsync imasc = new ImageAsync();
//		imasc.execute(f.toString());
//		
//		bitmap.recycle();
//		
		
		

		
//		mCloudRecognition.searchWithImage(COLLECTION_TOKEN,image);
//		startActivity(new Intent(RecognitionOnlyActivity.this, MainActivityVedio.class));
		/**
		 * chary
		 * Need to write service code to send image.
		 */
		
	}
	
	private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	
	class ImageAsync extends AsyncTask<String, String, String>
	{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params)
		{
			UploadToServer upServer = new UploadToServer();
			upServer.uploadFile(f.toString());
			return null;
		}
		
	}
	
	@Override
	public void requestImageError(String error) {
		//Take picture failed
		Toast.makeText(getBaseContext(),getString(R.string.recognition_only_toast_picture_error), Toast.LENGTH_SHORT).show();
		mScanningLayout.setVisibility(View.GONE);
		mTapToScanLayout.setVisibility(View.VISIBLE);
		mCamera.restartCameraPreview();
	}

	@Override
	public void onClick(View v) 
	{
		if (v == mTapToScanLayout) 
		{
//			startActivity(new Intent(RecognitionOnlyActivity.this, VideoActivity.class));
			mTapToScanLayout.setVisibility(View.GONE);
			mScanningLayout.setVisibility(View.VISIBLE);
			mCamera.takePicture();
		}

	}


}
