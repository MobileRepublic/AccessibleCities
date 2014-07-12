package com.acd.accessapp;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;



public class ImageComparision extends Activity 
{
	// Asset video file name.
		private static final String FILE_NAME ="Sample.mp4";
		
		protected void onCreate(android.os.Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			 try 
			 {
				AssetFileDescriptor afd = getAssets().openFd(FILE_NAME);
			} 
			 catch (IOException e)
			 {
				e.printStackTrace();
			}
		}
			
}
	
