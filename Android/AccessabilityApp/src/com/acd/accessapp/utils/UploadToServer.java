package com.acd.accessapp.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;
  
public class UploadToServer{
     
    private int serverResponseCode = 0;
	String serverResponseMessage = "";

	public  String upLoadServerUri	=	"http://ec2-54-79-7-64.ap-southeast-2.compute.amazonaws.com/melbourne/imagecompare/imagescan";
   
    public String uploadFile(String sourceFileUri) {
    	
    	Log.e("", "sourceFileUri "+sourceFileUri);
		
		String fileName = sourceFileUri;
		
		HttpURLConnection conn = null;
		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(sourceFileUri); 
		
		if (!sourceFile.isFile()) {
			
			return "";
			
		}else{
			
			try { 
				
				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL(upLoadServerUri);
				
				// Open a HTTP  connection to  the URL
				conn = (HttpURLConnection) url.openConnection(); 
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("image_name", fileName); 
				
				dos = new DataOutputStream(conn.getOutputStream());
				
				dos.writeBytes(twoHyphens + boundary + lineEnd); 
				dos.writeBytes("Content-Disposition: form-data; name=\"image_name\";filename=\""	+ fileName + "\"" + lineEnd);
				
				dos.writeBytes(lineEnd);
				
				// create a buffer of  maximum size
				bytesAvailable = fileInputStream.available(); 
				
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				
				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
				
				while (bytesRead > 0) {
					
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
					
				}
				
				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				
				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				
//				StatusLine statusLine = response.getStatusLine();
//				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					response.getEntity().writeTo(out);
//					out.close();
//					responseMessage = out.toString();
//					responce.reponce = responseMessage;
//					responce.error = null;
//				}

				//07-12 18:31:56.440: I/uploadFile(614): HTTP Response is : {"status":"Fail","result":"Images are not matched"}: 200
				//07-12 20:19:34.271: E/(4985): result {"id":"9","image_name":"20140711_225725.jpg","content":"test 9 content"}

				if(serverResponseCode == 200){
					
//					serverResponseMessage = conn.getContent().toString();
					InputStream in = conn.getInputStream();
					
				      int ch;
					 StringBuffer sb = new StringBuffer();
				      while ((ch = in.read()) != -1) {
				        sb.append((char) ch);
				      }
				      serverResponseMessage =  sb.toString();
				      Log.i("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
					
					         
				}    
				
				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();
				
			} catch (MalformedURLException ex) {
				
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
				
			} catch (Exception e) {
				
				Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e); 
				
			}
			
			return serverResponseMessage; 
			
		} // End else block 
	} 
}