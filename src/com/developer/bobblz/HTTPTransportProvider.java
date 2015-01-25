package com.developer.bobblz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;

public class HTTPTransportProvider<T> implements ITransportProvider<T> {
	
	private HttpGet httpGet;
	private HttpClient httpClient;
	private HttpResponse response;
	private Class<T> classInstance;
	
	public HTTPTransportProvider(Class<T> classInstance) {
		this.classInstance = classInstance;
	}
	
	@Override
	public boolean connect() {
		try {
			response = httpClient.execute(httpGet);
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
        
        if (setUrl() && connect()) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() {
		return getAll(null);
	}

	@Override
	public boolean create(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
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
	
	private boolean setUrl() {
		try {
			httpGet.setURI(new URI(Urls.ONE_ITEM_URL.toString()));
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
}
