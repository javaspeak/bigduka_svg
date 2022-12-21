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
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import com.javaspeak.bigduka.web.delegate.file.FileHelper;

public abstract class AbstractSvgGenerator implements SvgGenerator {

    public int getInteger( String attributeName, String text ){

        int indexAttribute = text.indexOf( " " + attributeName + "=" );

        int indexFirstQuote = text.indexOf( "\"", indexAttribute + 1 );
        int indexLastQuote = text.indexOf( "\"", indexFirstQuote + 1 );

        String integerStr = text.substring( indexFirstQuote + 1, indexLastQuote );

        integerStr = integerStr.replaceAll( "px", "" );

        int integer = Integer.parseInt( integerStr );

        return integer;
    }


    public String getSvgOpeningTag( String text ){

        int indexOfOpeningTag = text.indexOf( "<svg " );
        int endTag = text.indexOf( ">" );

        String svgTag = text.substring( indexOfOpeningTag, endTag + 1 );

        return svgTag;
    }



    public int getWidth( String text ){

        int width = getInteger( "width", text );
        return width;
    }


    public int getHeight( String text ){

        int height = getInteger( "height", text );
        return height;
    }


    public int getX( String text ){

        int x = getInteger( "x", text );
        return x;
    }


    public int getY( String text ){

        int y = getInteger( "y", text );
        return y;
    }


    public String populatePlaceHolders( FileHelper fileHelper, File file,
            Map<String,String> propertyMap ) throws IOException {

        String text =
            fileHelper.readTextFile( file );

        Set<String> keys = propertyMap.keySet();

        for ( String key : keys ){

            text = text.replaceAll( key,
                    Matcher.quoteReplacement( propertyMap.get( key ) ) );
        }

        return text;
    }
}
