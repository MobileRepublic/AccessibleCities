/**
 * 
 */
package com.acd.accessapp.utils;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

/**
 * @author C-Hall
 *
 */
public class TTSService extends Service
{

	TextToSpeech tts = null;
	ArrayList<String> placeNames = new ArrayList<String>();

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() 
	{
		tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
		{
			@Override
			public void onInit(int status)
			{
				tts.setLanguage(Locale.ENGLISH);
			}
		});
		super.onCreate();
	}

	/* (non-Javadoc)
	 */
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId)
	{
		Utils.RECIEVED_NAMES = placeNames;
		if(placeNames.size()>0)
		{
			for(String recName : placeNames)
			{
				tts.speak(recName, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		super.onStart(intent, startId);
	}
	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

}
