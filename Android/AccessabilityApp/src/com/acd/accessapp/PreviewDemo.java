package com.acd.accessapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acd.accessapp.utils.UploadToServer;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class PreviewDemo extends Activity implements SurfaceHolder.Callback, SurfaceTextureListener, SensorEventListener  {

	private TextView testView;
	private String LINK = "http://apps.accendotechnologies.com/imagereq/Sample.mp4";
	
	private Camera camera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private PictureCallback rawCallback;
	private ShutterCallback shutterCallback;
	private PictureCallback jpegCallback;
	private final String tag = "VideoServer";
	private TextureView textureView;
	private TextView scanAgain;
	private Button start, stop, capture;
	private SensorManager sensorManager;
	private ViewGroup _root;
	private int _xDelta = 300;
	private int _yDelta = 300;
	private int _zDelta = 150;
	private float _xSensor;
	private float _ySensor;
	private float _zSensor;
	private float _xSensorOld;
	private float _ySensorOld;
	private float _zSensorOld;
	
	private ProgressDialog progressdialog;
	
	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.preview);
		
		_root = (ViewGroup)findViewById(R.id.root);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(700, 300);
		layoutParams.leftMargin = _yDelta;
		layoutParams.topMargin =_zDelta;
		layoutParams.bottomMargin = -250;
		layoutParams.rightMargin = -250;
		
		textureView = (TextureView)findViewById(R.id.myTextureView);
		textureView.setLayoutParams(layoutParams);
		textureView.setSurfaceTextureListener(this);
		
		scanAgain = (TextView)findViewById(R.id.scanAgain);
		
		surfaceView = (SurfaceView)findViewById(R.id.surface);
		surfaceHolder = surfaceView.getHolder();
		
		scanAgain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				scanAgain.setVisibility(View.GONE);
				captureImage();
				
			}
		});
		
		surfaceHolder.addCallback(PreviewDemo.this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		rawCallback = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				Log.d("Log", "onPictureTaken - raw");
			}
		};
		
		/** Handles data for jpeg picture */
		shutterCallback = new ShutterCallback() {
			public void onShutter() {
				Log.i("Log", "onShutter'd");
			}
		};
		jpegCallback = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				
				
				String location = String.format(Environment.getExternalStorageDirectory().getAbsolutePath() +"/%d.jpg", System.currentTimeMillis());
				File file = new File(location);
				FileOutputStream outStream = null;
				try {
					outStream = new FileOutputStream(file);
					outStream.write(data);
					outStream.close();
					Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
					Log.d("Log", "file: " + file.getAbsolutePath());
					
					mPostImagePath = file.getAbsolutePath();
					new AsynPostData().execute();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
				}
				Log.d("Log", "onPictureTaken - jpeg");
			}
		};
		
		
		doDelay();
		
	}
	
	
	private boolean color = false;
	private View view;
	private long lastUpdate;
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//        getAccelerometer(event);
			
			_xSensor =  event.values[0];
			_ySensor = event.values[1];
			_zSensor = event.values[2];
			
			if (_xSensor > _xSensorOld) {
				_xDelta = _xDelta + 3;
			}else{
				_xDelta = _xDelta - 3;
			}
			
			_xSensorOld = _xSensor;
			
			if (_ySensor > _ySensorOld) {
				if (_yDelta < 320) {
					_yDelta =  _yDelta + 5;
				}
			}else{
				if (_yDelta > 280) {
					_yDelta =  _yDelta - 5;
				}
			}
			_ySensorOld = _ySensor;
			
			if (_zSensor > _zSensorOld) {
				if (_zDelta < 170) {
					_zDelta = _zDelta + 2;
				}
			}else{
				if (_zDelta > 130) {
					_zDelta = _zDelta - 2;
				}
			}
			
			_zSensorOld = _zSensor;
			
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(700, 300);
			layoutParams.leftMargin = _yDelta;
			layoutParams.topMargin =_zDelta;
			layoutParams.rightMargin = -250;
			layoutParams.bottomMargin = -250;
			textureView.setLayoutParams(layoutParams);
			_root.invalidate();
			
			
		}
	}
	
	public void onAccuracyChanged(int sensor, int accuracy) {
		Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
//		progressdialog = ProgressDialog.show(PreviewDemo.this, "","Please wait...", true);
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}
	
	private int mSplashDisplayDuration = 1*1000;	
	protected boolean mSplashInterrupted = false;
	
	@SuppressLint("NewApi")
	private void doDelay(){
		
		new Handler().postDelayed( new Runnable(){
			public void run() {
				if (!PreviewDemo.this.mSplashInterrupted)
				{
					
				
					
					start_camera();
					
					
					Log.e("", "  textureView.getScaleX() "+  textureView.getScaleX());
					Log.e("", "  textureView.getScaleY() "+  textureView.getScaleY());
					
					
				}
			}
		}, mSplashDisplayDuration);
	}
	
	@SuppressLint("NewApi")
	private void doCaptureDelay(){
		
		new Handler().postDelayed( new Runnable(){
			public void run() {
				if (!PreviewDemo.this.mSplashInterrupted)
				{
					captureImage();
					
				}
			}
		}, 1000);
	}
	
	
	SurfaceTexture surface;
	
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		
		this.surface = surface;
//    	doVideoDelay();
		
	}
	
	
	MediaPlayer mediaPlayer;
	private void playVidoe(String url){
		
		pictureFound = true;
//    	progressdialog.dismiss();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setSurface(new Surface(surface));
		try {
			Log.e("", "***********url" +url);
			Uri myUri = Uri.parse(url);
			
			textureView.setVisibility(View.VISIBLE);
			
			mediaPlayer.setDataSource(this, myUri);
			mediaPlayer.prepare();
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					
					pictureFound = false;
					scanAgain.setVisibility(View.VISIBLE);
					textureView.setVisibility(View.GONE);
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override 
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {return false;}
	@Override 
	public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {}
	@Override 
	public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}
	
	
	private void captureImage() {
		// TODO Auto-generated method stub
		camera.takePicture(shutterCallback, rawCallback, jpegCallback);
	}
	
	private void start_camera()
	{
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ALL),
				SensorManager.SENSOR_DELAY_GAME);
		try{
			camera = Camera.open();
		}catch(RuntimeException e){
			Log.e(tag, "init_camera: " + e);
			return;
		}
		Camera.Parameters param;
		param = camera.getParameters();
		//modify parameter
		param.setPreviewFrameRate(20);
		param.setPreviewSize(176, 144);
		camera.setParameters(param);
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
			doCaptureDelay();
			//camera.takePicture(shutter, raw, jpeg)
		} catch (Exception e) {
			Log.e(tag, "init_camera: " + e);
			return;
		}
	}
	
	private void stop_camera()
	{
		mediaPlayer.stop();
		mediaPlayer.release();
		
		try {
			camera.setPreviewDisplay(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		camera.stopPreview();
		camera.release();
		
		doDelayClose();
		
		
		
	}
	
	boolean flag = false;
	boolean pictureFound = false;
	
	private void doDelayClose(){
		
		new Handler().postDelayed( new Runnable(){
			public void run() {
				if (!PreviewDemo.this.mSplashInterrupted)
				{
					
					finish();
				}
			}
		}, mSplashDisplayDuration);
	}
	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	
	
//	****************************
//	Upload Files
//	****************************
	
	String mPostImagePath = "";
	private int mPostImagePosition = 0;
	
	
	class AsynPostData extends AsyncTask<Void, Void, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressdialog = ProgressDialog.show(PreviewDemo.this, "","Please wait...", true);
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			try
			{
				return new UploadToServer().uploadFile(mPostImagePath)+"";
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			
			
			if(progressdialog.isShowing())
			{
				progressdialog.cancel();
			}
			Log.e("", "result "+result);
			if(result !=null)
			{
				try {
					JSONObject resu = new JSONObject(result);
					if(resu.getString("status").equalsIgnoreCase("Fail"))
					{
						scanAgain.setVisibility(View.VISIBLE);
						Toast.makeText(PreviewDemo.this, "Image not match", 1000).show();
					}else{
						
						
						playVidoe(resu.getString("content"));
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	@Override
	public void onBackPressed() {
		
		if(!flag){
		
			try {
				
				if (!pictureFound) {
					
					playVidoe(LINK);
				}
				
				stop_camera();
				
			} catch (Exception e) {}
			
			flag = true;
			
		}
		
//		startActivity(new Intent(PreviewDemo.this, MainActivity.class));
//		finish();
//		super.onBackPressed();
		
		
	}
	
	
//	public boolean onTouch(View view, MotionEvent event) {
//		final int X = (int) event.getRawX();
//		final int Y = (int) event.getRawY();
//		switch (event.getAction() & MotionEvent.ACTION_MASK) {
//		case MotionEvent.ACTION_DOWN:
//			RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//			_xDelta = X - lParams.leftMargin;
//			_yDelta = Y - lParams.topMargin;
//			break;
//		case MotionEvent.ACTION_UP:
//			break;
//		case MotionEvent.ACTION_POINTER_DOWN:
//			break;
//		case MotionEvent.ACTION_POINTER_UP:
//			break;
//		case MotionEvent.ACTION_MOVE:
//			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//			layoutParams.leftMargin = X - _xDelta;
//			layoutParams.topMargin = Y - _yDelta;
//			layoutParams.rightMargin = -250;
//			layoutParams.bottomMargin = -250;
//			view.setLayoutParams(layoutParams);
//			break;
//		}
//		_root.invalidate();
//		return true;
//	}
	
}
