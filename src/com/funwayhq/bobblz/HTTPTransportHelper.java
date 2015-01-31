package com.funwayhq.bobblz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class HTTPTransportHelper {
	
	public static StringBuffer getStringBuffer(HttpResponse response) {
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            stringBuffer = new StringBuffer("");
            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();

            return stringBuffer;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
	
	public static boolean setUrl(HttpGet httpGet, String url) {
		try {
			if (httpGet != null) {
				httpGet.setURI(new URI(url));
			}
		
			return true;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean setUrl(HttpPost httpPost, String url) {
		try {
			if (httpPost != null) {
				httpPost.setURI(new URI(url));
			}
		
			return true;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void setPostEntity(HttpPost httpPost, IResource object) {
		String jString = JSONParserHelper.encodeOne(object);
		StringEntity entity = new StringEntity(jString, "UTF-8");
		httpPost.setEntity(entity);
	}
}
