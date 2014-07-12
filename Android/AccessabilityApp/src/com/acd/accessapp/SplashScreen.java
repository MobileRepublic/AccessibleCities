package com.acd.accessapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.acd.common.AppData;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);
		AppData.ctx = getApplicationContext();
		
		/*// Add code to print out the key hash
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo("com.janmakundali.news", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {
e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    }*/
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				startActivity(new Intent(SplashScreen.this, Launcher.class));
				finish();
				
			}
		}, 4000);
		
		
		
	}


}
