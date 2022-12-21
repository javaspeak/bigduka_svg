/*
    ============================================================================
    BigDuka is an e-commerce site targeted at small and medium shops who wish
    to have a web presence. BigDuka is a design from JavaSpeak.

    Copyright (C) 2010 javaspeak

	JavaSpeak's mission statement is to encourage the adoption of affordable
    e-commerce in Kenya across all segments of society.

	JavaSpeak and BigDuka are the "brain childs" of John Dickerson.

	Isaac Khaguli and John Dickerson are the technical members of BigDuka

	All code pertaining to the creation of BigDuka is the intellectual
	property of Isaac Khaguli and John Dickerson.

    www.javaspeak.com and www.bigduka.com are domain names owned by John
    Dickerson.  www.bigduka.co.ke is a domain name owned by Isaac Khaguli.

    ============================================================================
    Author : John Dickerson
    ============================================================================
*/
package com.javaspeak.bigduka.svg.generator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Advert implements ColumnElement {

	private File svgTemplateFile;
	private String text;
	private int height;

	/**
	 * key = name of property to replace in SvgTemplate file
	 *       e.g. ${someProperty}
	 *
	 * value = value to replace ${someProperty} with
	 */
	private Map<String,String> propertyMap;


	public File getSvgTemplateFile() {

		return svgTemplateFile;
	}


	public void setSvgTemplateFile(File svgTemplateFile) {

		this.svgTemplateFile = svgTemplateFile;
	}


	public Map<String, String> getPropertyMap() {

		if ( propertyMap == null ){

			propertyMap = new HashMap<String, String>();
		}

		return propertyMap;
	}


	public void setPropertyMap(Map<String, String> propertyMap) {

		this.propertyMap = propertyMap;
	}


	public void setText(String text) {

		this.text = text;
	}


	public String getText() {

		return text;
	}


	public int getHeight() {

		return height;
	}


	public void setHeight(int height) {

		this.height = height;
	}
}
