package com.acd.accessapp;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi") 
public class VideoActivity extends Activity implements SurfaceTextureListener 
{
	
	// Log tag.
	private static final String TAG = MainActivity.class.getName();

	// Asset video file name.
	private static final String FILE_NAME ="Sample.mp4";
			//"big_buck_bunny.mp4";

	// MediaPlayer instance to control playback of video file.
	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_vedio);

		
		 initView();
	}


	private void initView() 
	{
		 TextureView textureView = (TextureView) findViewById(R.id.textureView);
		    textureView.setSurfaceTextureListener(this);
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (mMediaPlayer != null) {
	        mMediaPlayer.stop();
	        mMediaPlayer.release();
	        mMediaPlayer = null;
	    }
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,int height) 
	{
		Surface surfaces = new Surface(surface);
		 try {
		        AssetFileDescriptor afd = getAssets().openFd(FILE_NAME);
		        mMediaPlayer = new MediaPlayer();
		        mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//		        mMediaPlayer.setDataSource(MainActivity.this,Uri.parse("http://apps.accendotechnologies.com/imagereq/Sample.mp4"),null);
		        mMediaPlayer.setSurface(surfaces);
		        mMediaPlayer.setLooping(false);
		        mMediaPlayer.prepareAsync();

		        // Play video when the media source is ready for playback.
		        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
		            @Override
		            public void onPrepared(MediaPlayer mediaPlayer) 
		            {
		                mediaPlayer.start();
		            }
		        });

		    } catch (IllegalArgumentException e) {
		        Log.d(TAG, e.getMessage());
		    } catch (SecurityException e) {
		        Log.d(TAG, e.getMessage());
		    } catch (IllegalStateException e) {
		        Log.d(TAG, e.getMessage());
		    } catch (IOException e) {
		        Log.d(TAG, e.getMessage());
		    }
	}


	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,int height) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) 
	{
		// TODO Auto-generated method stub
		
	}

}
