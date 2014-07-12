/**
 * 
 */
package com.acd.accessapp.utils;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.speech.tts.TextToSpeech;

/**
 * @author C-Hall
 *
 */
public class Utils extends Activity 
{
	public static ArrayList<String> RECIEVED_NAMES;
	
	TextToSpeech tts = null;
	
	public void textToSpeech()
	{
		tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() 
		{
			@Override
			public void onInit(int status) 
			{
				if(status == TextToSpeech.SUCCESS)
				{
					tts.setLanguage(Locale.ENGLISH);
					speakOut();
				}
			}
			private void speakOut()
			{
				if(RECIEVED_NAMES.size()>0)
				{
					for(String recievedName : RECIEVED_NAMES)
					{
						tts.speak(recievedName, TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
		});
	}
}
