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

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragmentActivity extends android.support.v4.app.FragmentActivity
{
	
	/**
	 * API Key For Android Apps - AIzaSyDLg_phf-b-D1qiSWaEGjunXc68nnI1hws
	 * API Key For Browser Apps - AIzaSyDvK3b4BMGo6EAznEfo3AM5bigYK1WawIw 
	 */
	GoogleMap googleMap = null;
	
	LocationManager lm = null;
	
	double latitude = 0;
	double longitude = 0;
	
	TextToSpeech tts = null;
	
	ArrayList<String> placeNames = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapfragment);
		
		intializeMap();
		
	}
	
	private void intializeMap()
	{
		if(googleMap == null)
		{
			googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, new LocationListener() 
			{
				
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) 
				{
					
				}
				
				@Override
				public void onProviderEnabled(String provider)
				{
					
				}
				
				@Override
				public void onProviderDisabled(String provider) 
				{
					
				}
				
				@Override
				public void onLocationChanged(Location location)
				{
					 latitude = location.getLatitude();
					 longitude = location.getLongitude();
					
					
					LatLng position = new LatLng(latitude, longitude);
					
					MarkerOptions mOptions = new MarkerOptions();
					
					mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_icon));
					
					mOptions.position(position);
					
					mOptions.title("Position");
					
					mOptions.snippet("Lat : "+ latitude  +"Lng "+ longitude);
					
					googleMap.addMarker(mOptions);
					
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOptions.getPosition(), 15));
					
					CircleOptions circle = new CircleOptions();
					circle.center(position);
					circle.radius(10000);
					circle.strokeColor(Color.BLUE);
					circle.strokeWidth(3);
					circle.fillColor(Color.parseColor("#500084d3"));
					
					googleMap.addCircle(circle);
					
					getNearestLocations( latitude, longitude);
					
				}

				public void getNearestLocations(double latitude,double longitude)
				{

					StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
					sb.append("location="+latitude+","+longitude);
					sb.append("&radius=5000");
					sb.append("&sensor=true");
					sb.append("&key=AIzaSyDvK3b4BMGo6EAznEfo3AM5bigYK1WawIw");
					
					
					// Creating a new non-ui thread task to download Google place json data 
			        PlacesTask placesTask = new PlacesTask();		        			        
			        
					// Invokes the "doInBackground()" method of the class PlaceTask
			        placesTask.execute(sb.toString());
					
				}
			});
		}
	}
	
	/** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException
	{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);                
                

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();                

                // Connecting to url 
                urlConnection.connect();                

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }

                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }

        return data;
    }         

	
	/** A class, to download Google Places */
	private class PlacesTask extends AsyncTask<String, Integer, String>
	{

		String data = null;
		
		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try{
				data = downloadUrl(url[0]);
			}catch(Exception e){
				 Log.d("Background Task",e.toString());
			}
			return data;
		}
		
		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result){			
			ParserTask parserTask = new ParserTask();
			
			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
		
	}
	
	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>
	{

		JSONObject jObject;
		
		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String,String>> doInBackground(String... jsonData)
		{
		
			List<HashMap<String, String>> places = null;			
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();
        
	        try{
	        	jObject = new JSONObject(jsonData[0]);
	        	
	            /** Getting the parsed data as a List construct */
	            places = placeJsonParser.parse(jObject);
	            
	        }catch(Exception e){
	                Log.d("Exception",e.toString());
	        }
	        return places;
		}
		
		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String,String>> list)
		{			
			
			// Clears all the existing markers 
//			googleMap.clear();
			
			if(list !=null)
			for(int i=0;i<list.size();i++)
			{
			
				// Creating a marker
	            MarkerOptions markerOptions = new MarkerOptions();
	            
	            // Getting a place from the places list
	            HashMap<String, String> hmPlace = list.get(i);
	
	            // Getting latitude of the place
	            double lat = Double.parseDouble(hmPlace.get("lat"));	            
	            
	            // Getting longitude of the place
	            double lng = Double.parseDouble(hmPlace.get("lng"));
	            
	            // Getting name
	            String name = hmPlace.get("place_name");
	            
	            placeNames.add(name);
	            
	            textToSpeech();
	            // Getting vicinity
	            String vicinity = hmPlace.get("vicinity");
	            
	            LatLng latLng = new LatLng(lat, lng);
	            
	            // Setting the position for the marker
	            markerOptions.position(latLng);
	
	            // Setting the title for the marker. 
	            //This will be displayed on taping the marker
	            markerOptions.title(name + " : " + vicinity);	            
	
	            // Placing a marker on the touched position
	            googleMap.addMarker(markerOptions);   
            
			}		
			
		}

		/**
		 * @param name
		 */
		private void textToSpeech()
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
					if(placeNames.size()>0)
					{
						for(String recName : placeNames)
						{
							tts.speak(recName, TextToSpeech.QUEUE_ADD, null);
						}
					}
					
						
					
				}
			});
		}
		
	}

}
