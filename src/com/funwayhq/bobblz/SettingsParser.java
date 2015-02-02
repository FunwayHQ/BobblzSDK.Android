package com.funwayhq.bobblz;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SettingsParser {

	public void parseUrl() {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("settings.xml");

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();

		try {
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(in);

			UrlBuilder.BASE_URL = doc.getElementsByTagName("base_url").item(0)
					.getTextContent();
			UrlBuilder.LIST_URL_ENDING = doc
					.getElementsByTagName("list_url_ending").item(0)
					.getTextContent();
			UrlBuilder.CREATE_URL_ENDING = doc
					.getElementsByTagName("create_url_ending").item(0)
					.getTextContent();
			UrlBuilder.SAVE_URL_ENDING = doc
					.getElementsByTagName("save_url_ending").item(0)
					.getTextContent();
			UrlBuilder.REMOVE_URL_ENDING = doc
					.getElementsByTagName("remove_url_ending").item(0)
					.getTextContent();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
