package com.firoj.shabnaz.ui;

import org.xml.sax.helpers.DefaultHandler;

import android.content.res.XmlResourceParser;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XmlParserClass extends DefaultHandler {
	private ArrayList<String> returnarraylist;
	private Map<String, String> returnValuesDictionary;
	Log log;

	public Map<String, String> getxmlArrays(XmlResourceParser xpp)
			throws XmlPullParserException, IOException {
		try {
			StringBuffer strbuffer = new StringBuffer();

			returnarraylist = new ArrayList<String>();
			returnValuesDictionary = new HashMap();
			String previousTag = "";

			xpp.next();
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					strbuffer.append("--- Start XML ---");
				} else if (eventType == XmlPullParser.START_TAG) {
					strbuffer.append("\nSTART_TAG: " + xpp.getName());
					previousTag = xpp.getName();

				} else if (eventType == XmlPullParser.END_TAG) {
					strbuffer.append("\nEND_TAG: " + xpp.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					strbuffer.append("\nTEXT: " + xpp.getText());

					if (previousTag.contains("URI")) {

						returnValuesDictionary.put(previousTag, xpp.getText());

						Log.d("XMLINS", "INSERTed" + xpp.getText());
					}

				}
				eventType = xpp.next();

			}

			return returnValuesDictionary;

		} catch (XmlPullParserException e) {
			Log.e("text", e.getMessage());
			return returnValuesDictionary;
		}

	}

	private class XmlParserReturnObjectClass {
		String Value;
		String Key;
	}

}
