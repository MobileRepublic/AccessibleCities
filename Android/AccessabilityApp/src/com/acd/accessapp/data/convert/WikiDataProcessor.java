/*
 * Copyright (C) 2012- Peer internet solutions & Finalist IT Group
 * 
 * This file is part of mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package com.acd.accessapp.data.convert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mixare.lib.HtmlUnescape;
import org.mixare.lib.marker.Marker;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.acd.accessapp.MixView;
import com.acd.accessapp.POIMarker;
import com.acd.accessapp.data.DataHandler;
import com.acd.accessapp.data.DataSource;
import com.acd.common.AppData;

/**
 * A data processor for wikipedia urls or data, Responsible for converting raw data (to json and then) to marker data.
 * @author A. Egal
 */
public class WikiDataProcessor extends DataHandler implements DataProcessor{

	public static final int MAX_JSON_OBJECTS = 1000;
	
	@Override
	public String[] getUrlMatch() {
		String[] str = {"wiki"};
		return str;
	}

	@Override
	public String[] getDataMatch() {
		String[] str = {"wiki"};
		return str;
	}
	
	@Override
	public boolean matchesRequiredType(String type) {
		if(type.equals(DataSource.TYPE.WIKIPEDIA.name())){
		//if(type.equals(DataSource.TYPE.LOCALSERVER.name())){
			return true;
		}
		return false;
	}

	@Override
	public List<Marker> load(String rawData, int taskId, int colour) throws JSONException {
		
		String jsonLocation = "";

		new AppData(AppData.ctx);
		
		try {
			
			boolean mToilets = false, mSuperstops = false, mPeopleTraffic = true;
			
			
//			Log.i("All Values",""+AppData.getSettings().Toilets+" "+AppData.getSettings().Hospitals+" "+AppData.getSettings().Scools);
			
			
			/*if(AppData.getSettings().Toilets)
			{
				mToilets = true;
			}else {
				mToilets = false;
			}
			
			if(AppData.getSettings().Hospitals)
			{
				mSuperstops = true;
			}else {
				mSuperstops = false;
			}
			
			if(AppData.getSettings().Scools)
			{
				mPeopleTraffic = true;
			}else {
				mPeopleTraffic = false;
			}*/
			
			
			if(mToilets && mSuperstops && mPeopleTraffic)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("toilets.json", AppData.ctx)+","+AssetJSONFile("superstop.json", AppData.ctx)+","+AssetJSONFile("traffic.json", AppData.ctx)+"]}";
			else if(mToilets && mSuperstops)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("toilets.json", AppData.ctx)+","+AssetJSONFile("superstop.json", AppData.ctx)+"]}";
			else if(mToilets && mPeopleTraffic)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("toilets.json", AppData.ctx)+","+AssetJSONFile("traffic.json", AppData.ctx)+"]}";
			else if(mPeopleTraffic && mSuperstops)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("traffic.json", AppData.ctx)+","+AssetJSONFile("superstop.json", AppData.ctx)+"]}";
			else if(mToilets)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("toilets.json", AppData.ctx)+"]}";
			else if( mSuperstops)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("superstop.json", AppData.ctx)+"]}";
			else if( mPeopleTraffic)
				jsonLocation = "{\"geonames\":["+AssetJSONFile("traffic.json", AppData.ctx)+"]}";
			
			Log.e("", "jsonLocation"+jsonLocation);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		List<Marker> markers = new ArrayList<Marker>();
		rawData = jsonLocation;
		JSONObject root = convertToJSON(rawData);
		JSONArray dataArray = root.getJSONArray("results");
//		JSONArray dataArray = root.getJSONArray("geonames");
		int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());
		
		Log.e("", "dataArray"+dataArray.toString());
		Log.e("", "top"+top);


		for (int i = 0; i < top; i++) {
			JSONObject jo = dataArray.getJSONObject(i);
			
			
			Log.e("", "jsonLocation"+jsonLocation);

			
			Marker ma = null;
			if (jo.has("sub_theme") && jo.has("latitude") && jo.has("longitude")&& jo.has("elevation") ) {
//			if (jo.has("title") && jo.has("lat") && jo.has("lng")&& jo.has("elevation") ) {
				
				Log.v(MixView.TAG, "processing Wikipedia JSON object");
		
				//no unique ID is provided by the web service according to http://www.geonames.org/export/wikipedia-webservice.html
				ma = new POIMarker(
						"",
						HtmlUnescape.unescapeHTML(jo.getString("sub_theme"), 0), 
						Double.parseDouble(jo.getString("latitude")),
						Double.parseDouble(jo.getString("longitude")),
						Double.parseDouble(jo.getString("elevation")),
						"", 
						taskId, 
						colour);
				markers.add(ma);
			}
		}
		return markers;
	}
	
	private JSONObject convertToJSON(String rawData){
		try {
			Log.e("raw data from lserver", rawData);
			return new JSONObject(rawData);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	 public static String AssetJSONFile (String filename, Context context) throws IOException {
	        AssetManager manager = context.getAssets();
	        InputStream file = manager.open(filename);
	        byte[] formArray = new byte[file.available()];
	        file.read(formArray);
	        file.close();

	        return new String(formArray);
	    }
	 
	 
	
}






///*
// * Copyright (C) 2012- Peer internet solutions & Finalist IT Group
// * 
// * This file is part of mixare.
// * 
// * This program is free software: you can redistribute it and/or modify it 
// * under the terms of the GNU General Public License as published by 
// * the Free Software Foundation, either version 3 of the License, or 
// * (at your option) any later version. 
// * 
// * This program is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
// * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
// * for more details. 
// * 
// * You should have received a copy of the GNU General Public License along with 
// * this program. If not, see <http://www.gnu.org/licenses/>
// */
//package com.acd.accessapp.data.convert;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.mixare.lib.HtmlUnescape;
//import org.mixare.lib.marker.Marker;
//
//import android.util.Log;
//
//import com.acd.accessapp.MixView;
//import com.acd.accessapp.POIMarker;
//import com.acd.accessapp.data.DataHandler;
//import com.acd.accessapp.data.DataSource;
//
///**
// * A data processor for wikipedia urls or data, Responsible for converting raw data (to json and then) to marker data.
// * @author A. Egal
// */
//public class WikiDataProcessor extends DataHandler implements DataProcessor{
//
//	public static final int MAX_JSON_OBJECTS = 1000;
//	
//	@Override
//	public String[] getUrlMatch() {
//		String[] str = {"wiki"};
//		return str;
//	}
//
//	@Override
//	public String[] getDataMatch() {
//		String[] str = {"wiki"};
//		return str;
//	}
//	
//	@Override
//	public boolean matchesRequiredType(String type) {
//		if(type.equals(DataSource.TYPE.WIKIPEDIA.name())){
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public List<Marker> load(String rawData, int taskId, int colour) throws JSONException {
//		List<Marker> markers = new ArrayList<Marker>();
//		JSONObject root = convertToJSON(rawData);
//		JSONArray dataArray = root.getJSONArray("geonames");
//		int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());
//
//		Log.e("", "dataArray"+dataArray.toString());
//
//		
//		for (int i = 0; i < top; i++) {
//			JSONObject jo = dataArray.getJSONObject(i);
//			
//			Marker ma = null;
//			if (jo.has("title") && jo.has("lat") && jo.has("lng")
//					&& jo.has("elevation") && jo.has("wikipediaUrl")) {
//	
//				Log.v(MixView.TAG, "processing Wikipedia JSON object");
//		
//				//no unique ID is provided by the web service according to http://www.geonames.org/export/wikipedia-webservice.html
//				ma = new POIMarker(
//						"",
//						HtmlUnescape.unescapeHTML(jo.getString("title"), 0), 
//						jo.getDouble("lat"), 
//						jo.getDouble("lng"), 
//						jo.getDouble("elevation"), 
//						"http://"+jo.getString("wikipediaUrl"), 
//						taskId, colour);
//				markers.add(ma);
//			}
//		}
//		return markers;
//	}
//	
//	private JSONObject convertToJSON(String rawData){
//		try {
//			return new JSONObject(rawData);
//		} catch (JSONException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	
//	
//}


