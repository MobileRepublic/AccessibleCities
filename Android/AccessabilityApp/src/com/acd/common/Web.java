package com.acd.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class Web {
	String responseMessage = "", error = null;
	private static final int REGISTRATION_TIMEOUT = 3 * 1000;
	private static final int WAIT_TIMEOUT = 30 * 1000;


	public Responce getMethod(String url) {

		Responce responce = new Responce();
		try {
			String URL = url;
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams params = httpclient.getParams();

			HttpConnectionParams.setConnectionTimeout(params,
					REGISTRATION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
			ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

			HttpGet httpPost = new HttpGet(URL);

			HttpResponse response = httpclient.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseMessage = out.toString();
				responce.reponce = responseMessage;
				responce.error = null;
			}

			else {
				// Closes the connection.
				Log.w("HTTP1:", statusLine.getReasonPhrase());
				response.getEntity().getContent().close();
				error = "error";
				responce.error = "error";
				responce.reponce = null;
				throw new IOException(statusLine.getReasonPhrase());
			}

		} catch (ClientProtocolException e) {
			Log.w("HTTP2:", e);
			error = "error";
			responce.error = "error";
			responseMessage = e.getMessage();
		} catch (IOException e) {
			Log.w("HTTP3:", e);
			error = "error";
			responce.error = "error";
			responce.reponce = null;
			responseMessage = e.getMessage();
		} catch (Exception e) {
			Log.w("HTTP4:", e);
			error = "error";
			responce.error = "error";
			responce.reponce = null;
			responseMessage = e.getMessage();
		}
		return responce;
	}
	
	
	
	public class Responce{
		public String error = null;
		public String reponce = null;
	}

}
