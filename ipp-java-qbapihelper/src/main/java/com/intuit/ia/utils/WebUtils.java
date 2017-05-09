/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ia.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class WebUtils {

	static public XmlPullParser createUTF8Parser(InputStream inputStream)
			throws XmlPullParserException {
		XmlPullParser parser = new KXmlParser();
		parser.setInput(inputStream, "UTF-8");
		return parser;
	}

	static public Document createXmlDocument(InputStream inputStream)
			throws IOException {
		Document document = null;
		try {
			XmlPullParser parser = createUTF8Parser(inputStream);
			parser.setInput(new InputStreamReader(inputStream));
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			document = new Document();
			document.parse(parser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return document;
	}

	public static String getElementText(Element element, String childElementName) {
		Element node = getElementByTagName(element, childElementName);
		if (node != null) {
			return getElementText(node);
		}
		return null;
	}

	static public String getElementText(Element element) {
		String elementText = null;

		if (element != null) {
			int numChildren = element.getChildCount();
			if (numChildren == 0) {
				elementText = null;
			} else if (numChildren == 1) {
				elementText = element.getText(0);
			} else {
				// compound value - treat as multiline
				StringBuffer stringBuffer = new StringBuffer();
				for (int i = 0; i < numChildren; i++) {
					Object childNode = element.getChild(i);
					if (childNode instanceof String) {
						stringBuffer.append(childNode);
					}
				}
				elementText = stringBuffer.toString();
			}
		}
		return elementText;
	}
	static public Element getElementByTagName(Element element,
			String elementName) {
		Element[] elements = getElementsByTagName(element, elementName);
		if (elements.length == 1) {
			return elements[0];
		} else {
			return null;
		}
	}

	static public Element[] getElementsByTagName(Element element,
			String elementName) {
		Vector<Element> elementList = new Vector<Element>();
		int lastFoundIndex = element.indexOf(null, elementName, 0);
		while (lastFoundIndex != -1) {
			elementList.addElement(element.getElement(lastFoundIndex));
			if (lastFoundIndex != -1) {
				lastFoundIndex = element.indexOf(null, elementName,
						lastFoundIndex + 1);
			}
		}
		Element[] elements = new Element[elementList.size()];
		elementList.copyInto(elements);
		return elements;
	}
	



}
