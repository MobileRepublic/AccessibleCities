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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mixare.lib.HtmlUnescape;
import org.mixare.lib.marker.Marker;

import android.os.Environment;
import android.util.Log;

import com.acd.accessapp.MixView;
import com.acd.accessapp.POIMarker;
import com.acd.accessapp.data.DataHandler;

/**
 * A data processor for custom urls or data, Responsible for converting raw data (to json and then) to marker data.
 * @author A. Egal
 */
public class MixareDataProcessor extends DataHandler implements DataProcessor{

	public static final int MAX_JSON_OBJECTS = 1000;
	
	@Override
	public String[] getUrlMatch() {
		String[] str = new String[0]; //only use this data source if all the others don't match
		return str;
	}

	@Override
	public String[] getDataMatch() {
		String[] str = new String[0]; //only use this data source if all the others don't match
		return str;
	}
	
	@Override
	public boolean matchesRequiredType(String type) {
		return true; //this datasources has no required type, it will always match.
	}

	@Override
	public List<Marker> load(String rawData, int taskId, int colour) throws JSONException {
		List<Marker> markers = new ArrayList<Marker>();
		JSONObject root = convertToJSON(rawData);
		JSONArray dataArray = root.getJSONArray("results");
		int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());

		try{
		writeToFile(rawData);
		}catch(Exception e){}
		
		
		for (int i = 0; i < top; i++) {
			JSONObject jo = dataArray.getJSONObject(i);
			
			Marker ma = null;
			/*if (jo.has("title") && jo.has("lat") && jo.has("lng")
					&& jo.has("elevation")) {

				String id = "";
				if(jo.has("id"))
						id = jo.getString("id");
				
				Log.v(MixView.TAG, "processing Mixare JSON object");
				String link=null;
		
				if(jo.has("has_detail_page") && jo.getInt("has_detail_page")!=0 && jo.has("webpage"))
					link=jo.getString("webpage");
				
				ma = new POIMarker(
						id,
						HtmlUnescape.unescapeHTML(jo.getString("title"), 0), 
						jo.getDouble("lat"), 
						jo.getDouble("lng"), 
						jo.getDouble("elevation"), 
						link, 
						taskId, colour);
				markers.add(ma);
			}*/
			
			if (jo.has("feature_name") && jo.has("latitude") && jo.has("longitude")&& jo.has("elevation") ) {
				
				Log.v(MixView.TAG, "processing Wikipedia JSON object");
		
				//no unique ID is provided by the web service according to http://www.geonames.org/export/wikipedia-webservice.html
				ma = new POIMarker(
						"",
						HtmlUnescape.unescapeHTML(jo.getString("feature_name"), 0), 
						Double.parseDouble(jo.getString("latitude")),
						Double.parseDouble(jo.getString("longitude")),
						jo.getInt("elevation"),
						"", 
						taskId, 
						colour);
				
				markers.add(ma);
			}
		}
		return markers;
	}
	
	
	public void writeToFile(String text)
	{
		

		try {

            final File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AccessibilityApp.txt") ;

            if (!myFile.exists()) {

            	myFile.createNewFile();
            }

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mms");
            String formattedDate = df.format(c.getTime());
            
            BufferedWriter buf = new BufferedWriter(new FileWriter(myFile, true)); 
            buf.append(text);
            buf.newLine();
            buf.close();
            
        } catch (Exception e) {
        	e.printStackTrace();
        }


}
	
	
	
	private JSONObject convertToJSON(String rawData){
		try {
			Log.e("rawdata", rawData);
			return new JSONObject(rawData);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
