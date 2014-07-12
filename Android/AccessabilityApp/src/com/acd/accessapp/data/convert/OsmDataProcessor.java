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
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.mixare.lib.marker.Marker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.os.Environment;
import android.util.Log;

import com.acd.accessapp.MixView;
import com.acd.accessapp.NavigationMarker;
import com.acd.accessapp.data.DataHandler;
import com.acd.accessapp.data.DataSource;

public class OsmDataProcessor extends DataHandler implements DataProcessor
{

	public static final int MAX_JSON_OBJECTS = 1000;

	@Override
	public String[] getUrlMatch() {
		String[] str = { "mapquestapi", "osm" };
		return str;
	}

	@Override
	public String[] getDataMatch() {
		String[] str = { "mapquestapi", "osm" };
		return str;
	}
	
	@Override
	public boolean matchesRequiredType(String type) {
		if(type.equals(DataSource.TYPE.OSM.name())){
			return true;
		}
		return false;
	}

	/**
	 * chary 
	 * Nearest Location Display. 
	 */
	@Override
	public List<Marker> load(String rawData, int taskId, int colour)
			throws JSONException {
		Element root = convertToXmlDocument(rawData).getDocumentElement();

		List<Marker> markers = new ArrayList<Marker>();
		NodeList nodes = root.getElementsByTagName("node");

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			NamedNodeMap att = node.getAttributes();
			NodeList tags = node.getChildNodes();
			for (int j = 0; j < tags.getLength(); j++) {
				Node tag = tags.item(j);
				if (tag.getNodeType() != Node.TEXT_NODE) {
					String key = tag.getAttributes().getNamedItem("k")
							.getNodeValue();
					if (key.equals("name")) {

						String name = tag.getAttributes().getNamedItem("v")
								.getNodeValue();
						String id = att.getNamedItem("id").getNodeValue();
						double lat = Double.valueOf(att.getNamedItem("lat")
								.getNodeValue());
						double lon = Double.valueOf(att.getNamedItem("lon")
								.getNodeValue());
						Log.v(MixView.TAG, "OSM Node: " + name + " lat " + lat	+ " lon " + lon + "\n");
						
						writeToFile(name + " lat " + lat	+ " lon " + lon + "\n");

						Marker ma = new NavigationMarker(
								id,
								name,
								lat,
								lon,
								0,
								"http://www.openstreetmap.org/?node="+ id,
								taskId, colour);
						markers.add(ma);
						
//						Sample.textToSpeech(name);

						// skip to next node
						continue;
					}
				}
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
	
	public Document convertToXmlDocument(String rawData) {
		Document doc = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// Document doc = builder.parse(is);d
			doc = builder.parse(new InputSource(new StringReader(rawData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

}
