package com.developer.bobblz;

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

public class HTTPTransportProvider<T> implements ITransportProvider<T> {
	
	private HttpGet httpGet;
	private HttpPost httpPost;
	private HttpClient httpClient;
	private HttpResponse response;
	private Class<T> classInstance;
	
	public HTTPTransportProvider(Class<T> classInstance) {
		this.classInstance = classInstance;
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
	public T getOne(BZCriteria criteria) {
		httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet();
        
        if (setUrl(Urls.ONE_ITEM_URL.toString()) && connect()) {
        	StringBuffer stringBuffer = getStringBuffer(response);
        	return parseResponse(stringBuffer.toString());
        }
        
		return null;
	}

	@Override
	public T getOne() {
		return getOne(null);
	}

	@Override
	public List<T> getAll(BZCriteria criteria) {
		httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet();
        
        if (setUrl(Urls.LIST_URL.toString()) && connect()) {
        	StringBuffer stringBuffer = getStringBuffer(response);
        	return parseAllResponse(stringBuffer.toString());
        }
        
		return null;
	}

	@Override
	public List<T> getAll() {
		return getAll(null);
	}

	@Override
	public boolean create(T object) {
		return makePostRequest(object, Urls.CREATE_URL.toString());
	}

	@Override
	public boolean save(T object) {
		return makePostRequest(object, Urls.SAVE_URL.toString());
	}

	@Override
	public boolean remove(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public boolean startListening() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private StringBuffer getStringBuffer(HttpResponse response) {
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
	
	private boolean setUrl(String url) {
		try {
			if (httpGet != null) {
				httpGet.setURI(new URI(url));
			}
			
			if (httpPost != null) {
				httpPost.setURI(new URI(url));
			}
			return true;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private T parseResponse(String jString) {
		JSONDataProcessor<T> dataProcessor = new JSONDataProcessor<>();
		try {
			T object = dataProcessor.decode(jString, classInstance);
			return object;
		} catch (JSONException | InstantiationException
				| IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	private List<T> parseAllResponse(String jString) {
		try {
			JSONArray jsonArray = new JSONArray(jString);
			List<T> dataList = new ArrayList<>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				T oneItem = parseResponse(object.toString());
				
				if (oneItem != null) {
					dataList.add(parseResponse(object.toString()));
				} else {
					return null;
				}
			}
			
			return dataList;
		} catch (JSONException | SecurityException e) {	
			e.printStackTrace();
			return null;
		}
	}
	
	private void setPostEntity(T object) {
		String jString = formJsonStrig(object);
		StringEntity entity = new StringEntity(jString, "UTF-8");
		httpPost.setEntity(entity);
	}
	
	private String formJsonStrig(T object) {
		JSONDataProcessor<T> dataProcessor = new JSONDataProcessor<>();
		try {
			return dataProcessor.encode(object);
		} catch (JSONException | IllegalAccessException
				| SecurityException e) {
	
			e.printStackTrace();
			return null;
		}
	}
	
	private int getResponseCode(String jString) {
		JSONObject object = new JSONObject(jString);
		return object.getInt("status");
	}
	
	private boolean makePostRequest(T object, String url) {
		httpClient = HttpClientBuilder.create().build();
        httpPost = new HttpPost();
        setPostEntity(object);
        
        if (setUrl(url) && connect()) {
        	StringBuffer stringBuffer = getStringBuffer(response);
        	int status = getResponseCode(stringBuffer.toString());
        	
        	// TODO check if the status corresponds to the action was done succefully
        	return true;
        }
		
		return false;
	}
}
