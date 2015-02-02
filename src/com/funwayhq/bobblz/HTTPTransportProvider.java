package com.funwayhq.bobblz;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

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
	public String getOne(String className, BZCriteria criteria) {
		return makeGetRequest(UrlBuilder.getOneItemUrl(className, criteria));
	}

	@Override
	public String getOne(String className) {
		return makeGetRequest(UrlBuilder.getOneItemUrl(className));
	}

	@Override
	public String getAll(String className, BZCriteria criteria) {
		return makeGetRequest(UrlBuilder.getListUrl(className, criteria));
	}

	@Override
	public String getAll(String className) {
		return makeGetRequest(UrlBuilder.getListUrl(className));
	}

	@Override
	public boolean create(String className, String encodedData) {
		return makePostRequest(encodedData, UrlBuilder.getCreateUrl(className));
	}

	@Override
	public boolean save(String className, String encodedData,
			BZCriteria criteria) {
		return makePostRequest(encodedData,
				UrlBuilder.getSaveUrl(className, criteria));
	}

	@Override
	public boolean remove(String className, BZCriteria criteria) {
		String answer = makeGetRequest(UrlBuilder.getRemoveUrl(className,
				criteria));
		int status = HTTPTransportHelper.getResponseCode(answer);

		// TODO check if the status corresponds to the action was done
		// succefully
		return true;
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

	private boolean makePostRequest(String encodedData, String url) {
		httpClient = HttpClientBuilder.create().build();
		httpPost = new HttpPost();

		StringEntity entity = new StringEntity(encodedData, "UTF-8");
		httpPost.setEntity(entity);

		if (HTTPTransportHelper.setUrl(httpPost, url) && connect()) {
			StringBuffer stringBuffer = HTTPTransportHelper
					.getStringBuffer(response);
			int status = HTTPTransportHelper.getResponseCode(stringBuffer
					.toString());

			// TODO check if the status corresponds to the action was done
			// succefully
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
			StringBuffer stringBuffer = HTTPTransportHelper
					.getStringBuffer(response);
			close();
			return stringBuffer.toString();
		}

		close();
		return null;
	}
}
