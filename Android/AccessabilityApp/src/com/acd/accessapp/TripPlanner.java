package com.acd.accessapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.acd.common.Web;
import com.acd.common.Web.Responce;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TripPlanner extends FragmentActivity implements OnClickListener {
	GoogleMap map;
	ArrayList<LatLng> markerPoints;
	double latitude = 0;
	double longitude = 0;
	EditText etFrom, etTo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tripplanner);

		ImageView btnExplore = (ImageView) findViewById(R.id.btnExplore);
		btnExplore.setOnClickListener(this);

		ImageView ivSearch = (ImageView) findViewById(R.id.ivSearch);
		ivSearch.setOnClickListener(this);

		etFrom = (EditText) findViewById(R.id.etFrom);
		etTo = (EditText) findViewById(R.id.etTo);

		// Initializing
		markerPoints = new ArrayList<LatLng>();

		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		// Getting Map for the SupportMapFragment
		map = fm.getMap();

		if (map != null) {

			// Enable MyLocation Button in the Map
			map.setMyLocationEnabled(true);
			
			map.getUiSettings().setZoomControlsEnabled(false);

			// Setting onclick event listener for the map
			/*
			 * map.setOnMapClickListener(new OnMapClickListener() {
			 * 
			 * @Override public void onMapClick(LatLng point) {
			 * 
			 * // Already two locations if (markerPoints.size() > 1) {
			 * markerPoints.clear(); map.clear(); }
			 * 
			 * // Adding new item to the ArrayList markerPoints.add(point);
			 * 
			 * // Creating MarkerOptions MarkerOptions options = new
			 * MarkerOptions();
			 * 
			 * // Setting the position of the marker options.position(point);
			 *//**
			 * For the start location, the color of marker is GREEN and for
			 * the end location, the color of marker is RED.
			 */
			/*
			 * if (markerPoints.size() == 1) {
			 * options.icon(BitmapDescriptorFactory
			 * .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); } else if
			 * (markerPoints.size() == 2) { options.icon(BitmapDescriptorFactory
			 * .defaultMarker(BitmapDescriptorFactory.HUE_RED)); }
			 * 
			 * // Add new marker to the Google Map Android API V2
			 * map.addMarker(options);
			 * 
			 * // Checks, whether start and end locations are captured if
			 * (markerPoints.size() >= 2) { LatLng origin = markerPoints.get(0);
			 * LatLng dest = markerPoints.get(1);
			 * 
			 * // Getting URL to the Google Directions API String url =
			 * getDirectionsUrl(origin, dest);
			 * 
			 * DownloadTask downloadTask = new DownloadTask();
			 * 
			 * // Start downloading json data from Google Directions // API
			 * downloadTask.execute(url); }
			 * 
			 * } });
			 */
		}

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

						String address = getAdderss(latitude, longitude);

						etTo.setText("" + address);

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

	LocationManager lm = null;

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	private String getDirectionsUrl(double sLat, double sLang, double dLat,
			double dLang) {

		// Origin of route
		String str_origin = "origin=" + sLat + "," + sLang;

		// Destination of route
		String str_dest = "destination=" + dLat + "," + dLang;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	String strResponce = null;
	
	
	class getLatLangFromAddress extends AsyncTask<String, Responce, Responce>
	{
		String address;
		
		ProgressDialog pDialg;

		public getLatLangFromAddress(String address) {
			// TODO Auto-generated constructor stub
			this.address = address;
		}
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialg = ProgressDialog.show(TripPlanner.this, "", "fetching...."+address);
			pDialg.setCancelable(true);
			
		}
		
		@Override
		protected Responce doInBackground(String... params) {
			
			Responce resp;
			resp = new Web().getMethod("http://maps.googleapis.com/maps/api/geocode/json?address="+ address.replaceAll(" ", "%20") + "&sensor=true_or_false");
			
			return resp;
		}
		
		@Override
		protected void onPostExecute(Responce strResponce) {
			super.onPostExecute(strResponce);
			

			if(pDialg.isShowing())
			{
				pDialg.cancel();
			}
			
			Log.e("downloadUrl", "" + strResponce.reponce);
			if (strResponce.error != null) {
				Toast.makeText(TripPlanner.this,
						"No Location found with your address",
						Toast.LENGTH_LONG).show();
				return;
			}

			JSONObject joMain;
			try {
				joMain = new JSONObject(strResponce.reponce);

				JSONArray jArray = joMain
						.getJSONArray("results");

				if (jArray.length() > 0) {

					JSONObject joSub = jArray.getJSONObject(0);
					if (!joSub.isNull("geometry")) {
						JSONObject geometry = joSub.getJSONObject("geometry");
						if (!geometry.isNull("location")) {
							JSONObject location = geometry.getJSONObject("location");

							if (!location.isNull("lat")) {
								double dLat = location.getDouble("lat");
								double dLan = location.getDouble("lng");

								getLatLang(dLat, dLan);
							}

						} else {
							Toast.makeText(
									TripPlanner.this,
									"No Location found with your address",
									Toast.LENGTH_LONG).show();
						}

					}
				} else {
					Toast.makeText(
							TripPlanner.this,
							"No Location found with your address",
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
					
		}
		
		
		
		
	}
	

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(10);
				lineOptions.color(Color.RED);

			}

			// Drawing polyline in the Google Map for the i-th route
			map.clear();
			map.addPolyline(lineOptions);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnExplore:

			startActivity(new Intent(TripPlanner.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT));

			break;

		case R.id.ivSearch:

			if (etTo.getText().length() > 0) {

				new getLatLangFromAddress(etFrom.getText().toString()).execute();

			}

			break;

		default:
			break;
		}

	}

	private void getLatLang(double dLat, double dLang) {

		LatLng position = new LatLng(dLat, dLang);

		MarkerOptions mOptions = new MarkerOptions();

		mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_icon));

		mOptions.position(position);

		mOptions.title("Position");

		mOptions.snippet("Lat : " + latitude + "Lng " + longitude);

		map.addMarker(mOptions);

		String url = getDirectionsUrl(latitude, longitude, dLat, dLang);

		DownloadTask downloadTask = new DownloadTask();

		// Start downloading json data from Google Directions
		// API
		downloadTask.execute(url);

	}

	public String getAdderss(double lat, double lang) {
		Geocoder geoCoder = new Geocoder(TripPlanner.this, Locale.ENGLISH);

		String address = "";

		try {

			List<Address> addresses = geoCoder.getFromLocation(latitude,
					longitude, 1);
			if (addresses != null && addresses.size() > 0) {
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder();

				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
					// .append(", ")
					strReturnedAddress
							.append(returnedAddress.getAddressLine(i));
					break;
				}
				address = strReturnedAddress.toString();
				// Toast.makeText(LocationMapActivity.this,
				// "Address is "+address, Toast.LENGTH_LONG).show();
			} else {
				address = "Address not found";
				// Toast.makeText(LocationMapActivity.this,
				// "Address is "+address, Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			e.printStackTrace();
			address = "Address not found";
		}

		Log.e("", "address" + address);
		return address;
	}

}
