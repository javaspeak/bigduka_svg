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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdvertSection implements ColumnElement, Comparable<AdvertSection> {

    private File svgTemplateFile;
    private String text;
    private int height;
    private Map<String,String> propertyMap = new HashMap<String,String>();

    private Map<String, List<Advert>> mapOfAdvertsByType =
        new HashMap<String,List<Advert>>();

    private String sectionNameSuitableForFileSystem;


    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( AdvertSection other ) {

        return this.sectionNameSuitableForFileSystem.compareTo(
                other.getSectionNameSuitableForFileSystem() );
    }


    public String getSectionNameSuitableForFileSystem() {

        return sectionNameSuitableForFileSystem;
    }


    public void setSectionNameSuitableForFileSystem(
            String sectionNameSuitableForFileSystem) {

        this.sectionNameSuitableForFileSystem =
                sectionNameSuitableForFileSystem;
    }


    public File getSvgTemplateFile() {

        return svgTemplateFile;
    }


    public void setSvgTemplateFile(File svgTemplateFile) {

        this.svgTemplateFile = svgTemplateFile;
    }


    public Map<String, String> getPropertyMap() {

        return propertyMap;
    }


    public void setPropertyMap(Map<String, String> propertyMap) {

        this.propertyMap = propertyMap;
    }


    public Set<String> getAdvertTypes(){

        return mapOfAdvertsByType.keySet();
    }


    public List<Advert> getAdvertsByType( String advertType ){

        if ( advertType == null ){

            throw new RuntimeException( "advertType cannot be null" );
        }

        List<Advert> adverts = mapOfAdvertsByType.get( advertType );

        if ( adverts == null ){

            adverts = new ArrayList<Advert>();
            mapOfAdvertsByType.put( advertType, adverts );
        }

        return adverts;
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
