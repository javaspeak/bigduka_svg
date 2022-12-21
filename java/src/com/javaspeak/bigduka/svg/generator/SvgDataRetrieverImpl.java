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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

import com.javaspeak.bigduka.web.delegate.file.FileHelper;

@Qualifier( "defaultSvgDataRetriever" )
@Component
public class SvgDataRetrieverImpl implements SvgDataRetriever , RetrieveData {

    public Logger logger = Logger.getLogger( SvgDataRetrieverImpl.class );

    private SortedSet<AdvertSection> advertSections;
    private File expandedZipDirectory;
    private FileHelper fileHelper;

    private boolean isSectionDirectory( File sectionDirectory ){

        File[] files = sectionDirectory.listFiles();
        Map<String,String> fileNames = new HashMap<String,String>();
        String fileName;

        for ( File file : files ){

            fileName = file.getName();

            if ( file.isFile() ){

                fileNames.put( fileName, fileName );
            }
        }

        String[] filesToCheckFor =
            { FREE_IMAGE_SVG_DATA_FILE_NAME, PAID_IMAGE_SVG_DATA_FILE_NAME,
                FREE_TEXT_SVG_DATA_FILE_NAME, PAID_TEXT_SVG_DATA_FILE_NAME,
                    SEGMENT_FILE_NAME };

        for ( String name : filesToCheckFor ){

            if ( ! fileNames.containsKey( name ) ){

                return false;
            }
        }

        return true;
    }


    /**
    private void validateSectionDirectory( File sectionDirectory )
        throws SvgDataRetrieverException {

        File[] files = sectionDirectory.listFiles();

        Map<String,String> directoryNames = new HashMap<String,String>();
        Map<String,String> fileNames = new HashMap<String,String>();
        String fileName;

        for ( File file : files ){

            fileName = file.getName();

            if ( file.isDirectory() ){

                directoryNames.put( fileName, fileName );
            }
            else {
                fileNames.put( fileName, fileName );
            }
        }

        StringWriter sw = new StringWriter();

        String[] filesToCheckFor =
            { FREE_IMAGE_SVG_DATA_FILE_NAME, PAID_IMAGE_SVG_DATA_FILE_NAME,
                FREE_TEXT_SVG_DATA_FILE_NAME, PAID_TEXT_SVG_DATA_FILE_NAME };

        boolean errors = false;

        for ( String name : filesToCheckFor ){

            if ( ! fileNames.containsKey( name ) ){

                errors = true;
                sw.append( "Cannot find, " ).append( name );
                sw.append( " for section, " );
                sw.append( sectionDirectory.getAbsolutePath() ).append( "\n" );
            }
        }

        if ( errors ){

            throw new SvgDataRetrieverException( sw.toString() );
        }
    }
    **/


    /**
    private void validateExpandedZipDirectory( File expandedZipDirectory )
        throws SvgDataRetrieverException{

        File[] files = expandedZipDirectory.listFiles();

        int directoryCount = 0;

        for ( File file : files ){

            if ( file.isDirectory() ){

                directoryCount++;
                validateSectionDirectory( file );
            }
        }

        if ( directoryCount == 0 ){

            throw new SvgDataRetrieverException(
                "No sections therefore cannot generate SVG" );
        }
    }
    **/


    private File getSvgFile( String svgFileName )
        throws SvgDataRetrieverException {

        try {
            URL url = this.getClass().getResource( svgFileName );

            File file = new File( url.toURI() );

            return file;
        }
        catch( URISyntaxException e ){

            throw new SvgDataRetrieverException(
                    "Could not find SVG template: " + svgFileName, e );
        }
    }


    private File getSvgFileForSectionAndPopulateTitleProperties(
        String[] titleLines, Map<String,String> propertyMap )
            throws SvgDataRetrieverException{

        propertyMap.put(
                AdvertSectionPropertyConstants.X, "0" );

        propertyMap.put(
                AdvertSectionPropertyConstants.Y, "0" );

        String svgFileName = "";

        if ( titleLines.length == 2 ){

            svgFileName = "section_two_line.svg";

            propertyMap.put(
                AdvertSectionPropertyConstants.TITLE_LINE_1,
                    titleLines[ 0 ] );

            propertyMap.put(
                    AdvertSectionPropertyConstants.TITLE_LINE_2,
                        titleLines[ 1 ] );
        }
        else if ( titleLines.length == 3 ){

            svgFileName = "section_three_line.svg";

            propertyMap.put(
                    AdvertSectionPropertyConstants.TITLE_LINE_1,
                        titleLines[ 0 ] );

            propertyMap.put(
                    AdvertSectionPropertyConstants.TITLE_LINE_2,
                        titleLines[ 1 ] );

            propertyMap.put(
                    AdvertSectionPropertyConstants.TITLE_LINE_3,
                        titleLines[ 2 ] );
        }
        else if ( titleLines.length == 1 ){

            svgFileName = "section_one_line.svg";

            propertyMap.put(
                    AdvertSectionPropertyConstants.TITLE_LINE_1,
                        titleLines[ 0 ] );
        }
        else {

            throw new SvgDataRetrieverException(
                "Unexpected title line length: " +
                    titleLines.length );
        }

        File svgFile = getSvgFile( svgFileName );

        return svgFile;
    }


    private File getSvgFileForImageAdvertAndPopulateDescriptionProperties(
        String[] descriptionLines,
            Map<String,String> propertyMap )
                throws SvgDataRetrieverException {

        String svgFileName = "";

        if ( descriptionLines.length == 3 ){

            svgFileName = "product_three_line.svg";

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_ONE,
                    descriptionLines[ 0 ] );

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_TWO,
                    descriptionLines[ 1 ] );

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_THREE,
                    descriptionLines[ 2 ] );
        }
        else if ( descriptionLines.length == 2 ){

            svgFileName = "product_two_line.svg";

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_ONE,
                    descriptionLines[ 0 ] );

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_TWO,
                    descriptionLines[ 1 ] );

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_THREE,
            "" );

        }
        else if ( descriptionLines.length == 1 ){

            svgFileName = "product_one_line.svg";

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_ONE,
                    descriptionLines[ 0 ] );

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_TWO,
                    "" );

            propertyMap.put( ImageAdvertPropertyConstants.DESCRIPTION_LINE_THREE,
                    "" );
        }
        else {

            throw new SvgDataRetrieverException(
                "Unexpected description line length: " +
                    descriptionLines.length );
        }

        File svgFile = getSvgFile( svgFileName );

        return svgFile;
    }


    private File getSvgFileForTextAdvertAndPopulateDescriptionProperties(
        String[] descriptionLines,
            Map<String,String> propertyMap ) throws SvgDataRetrieverException {

        String svgFileName = "";

        if ( descriptionLines.length == 3 ){

            svgFileName = "text_three_line.svg";

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_ONE,
                    descriptionLines[ 0 ] );

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_TWO,
                    descriptionLines[ 1 ] );

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_THREE,
                    descriptionLines[ 2 ] );
        }
        else if ( descriptionLines.length == 2 ){

            svgFileName = "text_two_line.svg";

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_ONE,
                    descriptionLines[ 0 ] );

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_TWO,
                    descriptionLines[ 1 ] );

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_THREE,
            "" );

        }
        else if ( descriptionLines.length == 1 ){

            svgFileName = "text_one_line.svg";

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_ONE,
                    descriptionLines[ 0 ] );

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_TWO,
                    "" );

            propertyMap.put( TextAdvertPropertyConstants.DESCRIPTION_LINE_THREE,
                    "" );
        }
        else {

            throw new SvgDataRetrieverException(
                "Unexpected description line length: " +
                    descriptionLines.length );
        }

        File svgFile = getSvgFile( svgFileName );

        return svgFile;
    }


    private void populateImagePositionAndDimensions(
        Map<String, String>propertyMap, String originalImageWidthStr,
            String originalImageHeightStr ){

        double sourceWidth = Double.parseDouble( originalImageWidthStr );
        double sourceHeight = Double.parseDouble( originalImageHeightStr );

        double rectangleWidth = 128;
        double rectangleHeight = 90;

        double destWidth = -1;
        double destHeight = -1;

        if ( rectangleHeight / rectangleWidth > sourceHeight / sourceWidth ){

            destWidth = rectangleWidth;

            destHeight =
                sourceHeight * ( rectangleWidth / sourceWidth );
        }
        else {

            destHeight = rectangleHeight;

            destWidth =
                sourceWidth * ( rectangleHeight / sourceHeight );
        }

        propertyMap.put(
             ImageAdvertPropertyConstants.IMAGE_WIDTH, ( ( int )destWidth -2 ) + "" );

        propertyMap.put(
                ImageAdvertPropertyConstants.IMAGE_HEIGHT, ( ( int )destHeight ) + "" );


        double x = 1 + ( rectangleWidth - destWidth ) / 2;
        double y = 1 + ( rectangleHeight - destHeight ) / 2;

        propertyMap.put( ImageAdvertPropertyConstants.IMAGE_X, ( ( int )x ) + "" );
        propertyMap.put( ImageAdvertPropertyConstants.IMAGE_Y, ( ( int )y ) + "" );
    }



    private AdvertSection createAdvertSection(
            String sectionName, String[] titleLines, File sectionDirectory )
                    throws SvgDataRetrieverException{

        AdvertSection advertSection = new AdvertSection();

        File svgTemplateFile =
            getSvgFileForSectionAndPopulateTitleProperties(
                titleLines, advertSection.getPropertyMap() );

        advertSection.setSvgTemplateFile( svgTemplateFile );

        sectionName =
            fileHelper.replaceIllegalCharactersForDirectoryName( sectionName );

        advertSection.setSectionNameSuitableForFileSystem( sectionName );

        File paidImageSvgData =
            new File( sectionDirectory, PAID_IMAGE_SVG_DATA_FILE_NAME );

        File freeImageSvgData =
            new File( sectionDirectory, FREE_IMAGE_SVG_DATA_FILE_NAME );

        File paidTextSvgData =
            new File( sectionDirectory, PAID_TEXT_SVG_DATA_FILE_NAME );

        File freeTextSvgData =
            new File( sectionDirectory, FREE_TEXT_SVG_DATA_FILE_NAME );

        addImageAdverts(
                PAID_IMAGE_ADVERT_TYPE, paidImageSvgData, advertSection );

        addImageAdverts(
                FREE_IMAGE_ADVERT_TYPE, freeImageSvgData, advertSection );

        addTextAdverts( PAID_TEXT_ADVERT_TYPE, paidTextSvgData, advertSection );

        addTextAdverts( FREE_TEXT_ADVERT_TYPE, freeTextSvgData, advertSection );

        return advertSection;
    }


    private void addImageAdverts( String advertType, File svgDataFile,
            AdvertSection advertSection ) throws SvgDataRetrieverException {

        try {
            int PRODUCT_NAME = 0;
            int IMAGE_PATH = 1;
            int ORIGINAL_IMAGE_WIDTH = 2;
            int ORIGINAL_IMAGE_HEIGHT = 3;
            int PRODUCT_DESCRIPTION = 4;
            int PRICE = 5;
            int PRODUCT_ID = 6;
            int CONTACT_NUMBER = 7;

            CSVReader cvsReader = new CSVReader( new FileReader( svgDataFile ) );
            String[] nextLine;
            List<Advert> adverts = advertSection.getAdvertsByType( advertType );
            Advert advert;
            Map<String,String> propertyMap;
            String[] descriptionLines;

            int maxNumberOfSameLetterOnLine = 21;
            String letter = "T";

            ArialFont arialFont =
                new ArialFont( maxNumberOfSameLetterOnLine, letter );

            File svgTemplateFile;

            while ( ( nextLine = cvsReader.readNext() ) != null ){

                advert = new Advert();
                propertyMap = advert.getPropertyMap();

                propertyMap.put(
                    ImageAdvertPropertyConstants.PRODUCT_NAME,
                            nextLine[ PRODUCT_NAME ] );

                propertyMap.put(
                    ImageAdvertPropertyConstants.IMAGE_PATH,
                            nextLine[ IMAGE_PATH ] );

                populateImagePositionAndDimensions(
                    propertyMap, nextLine[ ORIGINAL_IMAGE_WIDTH ],
                        nextLine[ ORIGINAL_IMAGE_HEIGHT ] );

                descriptionLines =
                    arialFont.getLines( nextLine[ PRODUCT_DESCRIPTION ] );

                svgTemplateFile =
                    getSvgFileForImageAdvertAndPopulateDescriptionProperties(
                            descriptionLines, propertyMap );

                advert.setSvgTemplateFile( svgTemplateFile );

                propertyMap.put(
                        ImageAdvertPropertyConstants.PRICE, nextLine[ PRICE ] );

                propertyMap.put(
                    ImageAdvertPropertyConstants.PRODUCT_ID,
                        nextLine[ PRODUCT_ID ] );

                propertyMap.put(
                    ImageAdvertPropertyConstants.TELEPHONE,
                        nextLine[ CONTACT_NUMBER ] );

                adverts.add( advert );
            }
        }
        catch( FileNotFoundException e ){

            throw new SvgDataRetrieverException( e );
        }
        catch( IOException e ){

            throw new SvgDataRetrieverException( e );
        }
    }


    private void addTextAdverts( String advertType, File svgDataFile,
            AdvertSection advertSection ) throws SvgDataRetrieverException {

        try {
            int PRODUCT_NAME = 0;
            int PRODUCT_DESCRIPTION = 1;
            int PRICE = 2;
            int PRODUCT_ID = 3;
            int CONTACT_NUMBER = 4;

            CSVReader cvsReader =
                new CSVReader( new FileReader( svgDataFile ) );

            String[] nextLine;
            List<Advert> adverts = advertSection.getAdvertsByType( advertType );
            Advert advert;
            Map<String,String> propertyMap;
            String[] descriptionLines;

            int maxNumberOfSameLetterOnLine = 21;
            String letter = "T";

            ArialFont arialFont =
                new ArialFont( maxNumberOfSameLetterOnLine, letter );

            File svgTemplateFile;

            while ( ( nextLine = cvsReader.readNext() ) != null ){

                advert = new Advert();
                propertyMap = advert.getPropertyMap();

                propertyMap.put(
                    TextAdvertPropertyConstants.PRODUCT_NAME,
                        nextLine[ PRODUCT_NAME ] );

                descriptionLines =
                    arialFont.getLines( nextLine[ PRODUCT_DESCRIPTION ] );

                svgTemplateFile =
                    getSvgFileForTextAdvertAndPopulateDescriptionProperties(
                        descriptionLines, propertyMap );

                advert.setSvgTemplateFile( svgTemplateFile );

                propertyMap.put(
                        ImageAdvertPropertyConstants.PRICE,
                            nextLine[ PRICE ] );

                propertyMap.put(
                      ImageAdvertPropertyConstants.PRODUCT_ID,
                          nextLine[ PRODUCT_ID ] );

                propertyMap.put(
                    ImageAdvertPropertyConstants.TELEPHONE,
                        nextLine[ CONTACT_NUMBER ] );

                adverts.add( advert );
            }
        }
        catch( FileNotFoundException e ){

            throw new SvgDataRetrieverException( e );
        }
        catch( IOException e ){

            throw new SvgDataRetrieverException( e );
        }
    }


    @Autowired
    public SvgDataRetrieverImpl( FileHelper fileHelper ){

        this.fileHelper = fileHelper;
    }


    /* (non-Javadoc)
     * @see com.javaspeak.bigduka.svg.generator.
     *         SvgDataRetriever#getAdvertSections()
     */
    public SortedSet<AdvertSection> getAdvertSections()
                throws SvgDataRetrieverException {

        // validateExpandedZipDirectory( this.expandedZipDirectory );

        advertSections = new TreeSet<AdvertSection>();

        File[] files = expandedZipDirectory.listFiles();
        File sectionFile;
        AdvertSection advertSection;
        CSVReader csvReader= null;
        String[] nextLine;
        String sectionName;
        String[] titleLines;

        for ( File sectionDirectory : files ){

            if ( isSectionDirectory( sectionDirectory ) ){

                sectionFile =
                    new File( sectionDirectory, SEGMENT_FILE_NAME );

                try {
                    csvReader = new CSVReader( new FileReader( sectionFile ) );

                    nextLine = csvReader.readNext();

                    sectionName = nextLine[ 0 ];

                    if ( nextLine.length == 2 ){

                        titleLines = new String[] { nextLine[ 1 ] };
                    }
                    else if ( nextLine.length > 2 ){

                        titleLines = new String[] { nextLine[ 1 ],
                                nextLine[ 2 ] };
                    }
                    else {
                        throw new RuntimeException( "Missformed csv file" );
                    }

                    advertSection =
                        createAdvertSection(
                                sectionName, titleLines, sectionDirectory );

                    logger.debug( "advertSection = " + advertSection );

                    advertSections.add( advertSection );
                }
                catch( FileNotFoundException e ){

                    throw new SvgDataRetrieverException(
                        "Could not find section file, " +
                            sectionFile.getAbsolutePath(), e );
                }
                catch( IOException e ){

                    throw new SvgDataRetrieverException(
                        "Could not read section file, " +
                            sectionFile.getAbsolutePath(),  e );
                }
                finally    {

                    if ( csvReader != null ){

                        try {
                            csvReader.close();
                        }
                        catch( IOException e ){

                            // do nothing
                        }
                    }
                }
            }
        }

        return advertSections;
    }


    /* (non-Javadoc)
     * @see com.javaspeak.bigduka.svg.generator.SvgDataRetriever#
     *         getCachedAdvertSections()
     */
    public SortedSet<AdvertSection> getCachedAdvertSections() {

        return advertSections;
    }


    public File getExpandedZipDirectory() {
        return expandedZipDirectory;
    }


    public void setExpandedZipDirectory( File expandedZipDirectory ) {

        this.expandedZipDirectory = expandedZipDirectory;
    }
}
