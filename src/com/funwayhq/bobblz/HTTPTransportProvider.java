package com.funwayhq.bobblz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTPTransportProvider implements ITransportProvider {
	
	private HttpGet httpGet;
	private HttpPost httpPost;
	private HttpClient httpClient;
	private HttpResponse response;

	
	@Override
	public boolean connect() {
		try {
			if (httpGet != null) {
				response = httpClient.execute(httpGet);
			}
			
			if (httpPost != null) {
				response = httpClient.execute(httpPost);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String getOne(BZCriteria criteria) {
		httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet();
        
        if (HTTPTransportHelper.setUrl(httpGet, Urls.ONE_ITEM_URL.toString()) && connect()) {
        	StringBuffer stringBuffer = HTTPTransportHelper.getStringBuffer(response);
        	return stringBuffer.toString();
        }
        
		return null;
	}

	@Override
	public String getOne() {
		return getOne(null);
	}

	@Override
	public String getAll(BZCriteria criteria) {
		httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet();
        
        if (HTTPTransportHelper.setUrl(httpGet, Urls.LIST_URL.toString()) && connect()) {
        	StringBuffer stringBuffer = HTTPTransportHelper.getStringBuffer(response);
        	return stringBuffer.toString();
        }
        
		return null;
	}

	@Override
	public String getAll() {
		return getAll(null);
	}

	@Override
	public boolean create(Object object) {
		return makePostRequest(object, Urls.CREATE_URL.toString());
	}

	@Override
	public boolean save(Object object) {
		return makePostRequest(object, Urls.SAVE_URL.toString());
	}

	@Override
	public boolean remove(Object object) {
		return makePostRequest(object, Urls.REMOVE_URL.toString());
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean startListening() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean makePostRequest(Object object, String url) {
		httpClient = HttpClientBuilder.create().build();
        httpPost = new HttpPost();
        HTTPTransportHelper.setPostEntity(httpPost, object);
        
        if (HTTPTransportHelper.setUrl(httpPost, url) && connect()) {
        	StringBuffer stringBuffer = HTTPTransportHelper.getStringBuffer(response);
        	int status = JSONParserHelper.getResponseCode(stringBuffer.toString());
        	
        	// TODO check if the status corresponds to the action was done succefully
        	return true;
        }
		
		return false;
	}
}
