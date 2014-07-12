package com.acd.accessapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.acd.common.AppData;
import com.acd.common.SettingsDo;
import com.actionbarsherlock.app.ActionBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Launcher extends FragmentActivity implements OnClickListener {

	ImageView btnTripPlanner, btnSettings, btnExplore;
	LocationManager lm = null;
	double latitude = 0;
	double longitude = 0;
	GoogleMap map;
	
	public ActionBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setTheme(R.style.Theme_MyTheme);
		setContentView(R.layout.mainscreensample);
		
		
		/*bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Accessible Cities");*/
		
		btnTripPlanner = (ImageView) findViewById(R.id.btnTripPlanner);
		btnExplore = (ImageView) findViewById(R.id.btnExplore);
		btnSettings = (ImageView) findViewById(R.id.btnSettings);

		btnTripPlanner.setOnClickListener(this);
		btnExplore.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		
		// Getting reference to SupportMapFragment of the activity_main
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map);

				// Getting Map for the SupportMapFragment
			map = fm.getMap();
			
			map.getUiSettings().setZoomControlsEnabled(false);
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100,
				new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}

					@Override
					public void onProviderEnabled(String provider) {

					}

					@Override
					public void onProviderDisabled(String provider) {

					}

					@Override
					public void onLocationChanged(Location location) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();

						LatLng position = new LatLng(latitude, longitude);

						MarkerOptions mOptions = new MarkerOptions();

						mOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.loc_icon));

						mOptions.position(position);

						mOptions.title("Position");

						mOptions.snippet("Lat : " + latitude + "Lng "
								+ longitude);

						map.addMarker(mOptions);

						map.animateCamera(CameraUpdateFactory.newLatLngZoom(
								mOptions.getPosition(), 15));

						/*
						 * CircleOptions circle = new CircleOptions();
						 * circle.center(position); circle.radius(10000);
						 * circle.strokeColor(Color.BLUE);
						 * circle.strokeWidth(3);
						 * circle.fillColor(Color.parseColor("#500084d3"));
						 */

						// map.addCircle(circle);

					}
				});
		
		
		
		

	}

	@Override
	protected void onResume() {
		super.onResume();
		new AppData(this);

		SettingsDo settings = AppData.getSettings();

		if (settings.Buildings || settings.Toilets || settings.Super_Stops) {

			Log.e("", "Enabled");

			btnTripPlanner.setEnabled(true);
			btnExplore.setEnabled(true);
			
			btnTripPlanner.setImageResource(R.drawable.tripplanner);
			btnExplore.setImageResource(R.drawable.explore);
			
		} else {
			btnTripPlanner.setEnabled(false);
			btnExplore.setEnabled(false);
			
			btnTripPlanner.setImageResource(R.drawable.tripplan_inact);
			btnExplore.setImageResource(R.drawable.explore_inact);
		}

	}

	@Override
	public void onClick(final View v) {
		
		v.setClickable(false);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				v.setClickable(true);
			}
		}, 150);

		switch (v.getId()) {
		case R.id.btnTripPlanner:

			startActivity(new Intent(Launcher.this, TripPlanner.class));

			break;
		case R.id.btnExplore:

			startActivity(new Intent(Launcher.this, MainActivity.class));

			break;
		case R.id.btnSettings:

			startActivity(new Intent(Launcher.this, Settings.class));

			break;

		default:
			break;
		}

	}

}
