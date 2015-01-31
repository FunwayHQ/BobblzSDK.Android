package com.funwayhq.bobblz;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class HTTPTransportProvider implements ITransportProvider {
	
	private HttpGet httpGet;
	private HttpPost httpPost;
	private HttpClient httpClient;
	private HttpResponse response;
	private Class<?> classObject;
	
	public HTTPTransportProvider(Class<?> classObject) {
		this.classObject = classObject;
	}
	
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
		return makeGetRequest(Urls.getOneItemUrl(classObject));
	}

	@Override
	public String getOne() {
		return getOne(null);
	}

	@Override
	public String getAll(BZCriteria criteria) {
		return makeGetRequest(Urls.getListUrl(classObject));
	}

	@Override
	public String getAll() {
		return getAll(null);
	}

	@Override
	public boolean create(IResource object) {
		return makePostRequest(object, Urls.getCreateUrl(object));
	}

	@Override
	public boolean save(IResource object) {
		return makePostRequest(object, Urls.getSaveUrl(object));
	}

	@Override
	public boolean remove(IResource object) {
		return makePostRequest(object, Urls.getRemoveUrl(object));
	}

	@Override
	public boolean close() {
		httpClient = null;
		httpPost = null;
		httpGet = null;
		return true;
	}

	@Override
	public boolean startListening() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean makePostRequest(IResource object, String url) {
		httpClient = HttpClientBuilder.create().build();
        httpPost = new HttpPost();
        HTTPTransportHelper.setPostEntity(httpPost, object);
        
        if (HTTPTransportHelper.setUrl(httpPost, url) && connect()) {
        	StringBuffer stringBuffer = HTTPTransportHelper.getStringBuffer(response);
        	int status = JSONParserHelper.getResponseCode(stringBuffer.toString());
        	
        	// TODO check if the status corresponds to the action was done succefully
        	close();
        	return true;
        }
		
        close();
		return false;
	}
	
	private String makeGetRequest(String url) {
		httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet();
        
        if (HTTPTransportHelper.setUrl(httpGet, url) && connect()) {
        	StringBuffer stringBuffer = HTTPTransportHelper.getStringBuffer(response);
        	close();
        	return stringBuffer.toString();
        }
        
        close();
		return null;
	}
} 
