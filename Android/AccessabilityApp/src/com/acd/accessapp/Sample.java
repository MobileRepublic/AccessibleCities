package com.acd.accessapp;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;

public class Sample extends Application 
{
	public static Context context;

	public static TextToSpeech tts = null;
	@Override
	public void onCreate()
	{
		super.onCreate();
		Sample.context = this.getApplicationContext();
	}
	
	public static void textToSpeech(String textStr)
	{
		tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() 
		{
			
			@Override
			public void onInit(int status)
			{
				tts.setLanguage(Locale.ENGLISH);
				tts.setPitch(20);
			}
		});
		
		tts.speak(textStr, TextToSpeech.QUEUE_FLUSH, null);
	}
	
}
