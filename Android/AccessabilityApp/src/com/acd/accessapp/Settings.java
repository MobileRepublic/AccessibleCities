package com.acd.accessapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.acd.common.AppData;
import com.acd.common.SettingsDo;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Settings extends SherlockFragmentActivity implements OnClickListener,
		OnCheckedChangeListener {

	CheckBox cbBuilding, cbToilets, cbScools, cbRamsBottomRight;
	CheckBox cbTFLeft, cbTFRight, cbTFBottomLeft, cbTFBottomRight;
	SettingsDo settigns;
	
	LocationManager lm = null;
	double latitude = 0;
	double longitude = 0;
	GoogleMap map;
	
//	public ActionBar bar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setTheme(R.style.Theme_MyTheme);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settingss);
		
		/*bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setIcon(R.drawable.navbarlogo);
		bar.setTitle("Settings");*/
		
		cbBuilding = (CheckBox) findViewById(R.id.cbBuilding);
		cbToilets = (CheckBox) findViewById(R.id.cbToilets);
		cbScools = (CheckBox) findViewById(R.id.cbScools);

		cbBuilding.setOnCheckedChangeListener(this);
		cbToilets.setOnCheckedChangeListener(this);
		cbScools.setOnCheckedChangeListener(this);

		ImageView btnTripPlanner = (ImageView) findViewById(R.id.btnTripPlanner);
		btnTripPlanner.setOnClickListener(this);
		ImageView btnExplore = (ImageView) findViewById(R.id.btnExplore);
		btnExplore.setOnClickListener(this);
		
		ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		

		new AppData(this);
		settigns = AppData.getSettings();

		if (settigns.Buildings) {
			cbBuilding.setChecked(true);

		}
		if (settigns.Toilets) {
			cbToilets.setChecked(true);

		}
		if (settigns.Super_Stops) {
			cbScools.setChecked(true);

		}
		
		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		// Getting Map for the SupportMapFragment
		map = fm.getMap();


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
	public void onClick(View v) {

		AppData.setUserModel(settigns);
		
		switch (v.getId()) {
		case R.id.btnTripPlanner:

			startActivity(new Intent(Settings.this, TripPlanner.class).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT));
			finish();

			break;

		case R.id.btnExplore:
			
			startActivity(new Intent(Settings.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT));
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		switch (buttonView.getId()) {
		case R.id.cbBuilding:

			Log.e("Hospitals",""+isChecked);
			settigns.Buildings = isChecked;

			break;
		case R.id.cbToilets:

			Log.e("Toilets",""+isChecked);
			settigns.Toilets = isChecked;

			break;
		case R.id.cbScools:

			Log.e("Scools",""+isChecked);
			settigns.Super_Stops = isChecked;

			break;
			
		default:
			break;
		}

	}

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AppData.setUserModel(settigns);
	}
	

}
