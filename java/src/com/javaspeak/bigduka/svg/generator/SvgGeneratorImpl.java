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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.javaspeak.bigduka.web.delegate.file.FileHelper;
import com.javaspeak.bigduka.web.delegate.file.FileHelperException;

@Component
@Qualifier( "defaultSvgGenerator" )
public class SvgGeneratorImpl extends AbstractSvgGenerator {

    public Logger logger = Logger.getLogger( SvgGeneratorImpl.class );

    private int widthColumnInPixels = 128;
    private int maxHeightColumnInPixels = 1322;
    private int verticalSpaceBetweenSvgsInPixels = 4;

    private FileHelper fileHelper;


    private void copyImageToSvgDirectoryToGenerateSvgFilesTo(
            Advert advert, AdvertSection advertSection, String advertType,
                File expandedZipDirectoryFile,
                    File svgDirectoryToGenerateSvgFilesTo )
                        throws SvgGeneratorException{

        String fileName =
            advert.getPropertyMap().get(
                    ImageAdvertPropertyConstants.IMAGE_PATH );

        if ( fileName != null ){

            File sourceFile =
              new File( expandedZipDirectoryFile, fileName );

            File sectionDirectory =
                new File( svgDirectoryToGenerateSvgFilesTo,
                        advertSection.getSectionNameSuitableForFileSystem() );

            if ( ! sectionDirectory.exists() ){

                if ( ! sectionDirectory.mkdir() ){

                    throw new SvgGeneratorException(
                            "Could not make directory, " +
                                sectionDirectory.getAbsolutePath() );
                }
            }

            File advertTypeDirectory = new File( sectionDirectory, advertType );

            if ( ! advertTypeDirectory.exists() ){

                if ( ! advertTypeDirectory.mkdir() ){

                    throw new SvgGeneratorException(
                            "Could not make directory, " +
                            advertTypeDirectory.getAbsolutePath() );
                }
            }

            String productName =
                advert.getPropertyMap().get(
                        ImageAdvertPropertyConstants.PRODUCT_NAME );

            productName =
                fileHelper.replaceIllegalCharactersForDirectoryName(
                        productName );

            String productId =
                advert.getPropertyMap().get(
                        ImageAdvertPropertyConstants.PRODUCT_ID );

            String advertDirectoryName = productId + "_" + productName;

            File advertDirectory =
                new File( advertTypeDirectory, advertDirectoryName );

            if ( ! advertDirectory.exists() ){

                if ( ! advertDirectory.mkdir() ){

                    throw new SvgGeneratorException(
                            "Could not make directory, " +
                            advertDirectory.getAbsolutePath() );
                }
            }

            fileName = fileHelper.getNameStrippedOfPath( fileName );

            File destinationFile = new File( advertDirectory, fileName );

            try {
                fileHelper.copyBinaryFile( sourceFile, destinationFile );
            }
            catch( FileHelperException e ){

                throw new SvgGeneratorException( "Could not copy image " +
                        sourceFile.getAbsolutePath() + " to " +
                            destinationFile.getAbsolutePath(), e );
            }
        }
    }


    private File createSvgFile(
              String svgText, int columnCountForSection,
                 File svgDirectoryToGenerateSvgFilesTo,
                  String sectionNameSuitableForFileSystem,
                      String advertType  )
                        throws SvgGeneratorException{

        try {
            String fileName =
                sectionNameSuitableForFileSystem + "_" +
                    advertType + "_column_" +
                        columnCountForSection + ".svg";

            File svgFile =
                new File( svgDirectoryToGenerateSvgFilesTo, fileName );

            fileHelper.writeTextFile( svgFile, svgText );

            logger.debug( "Created file, " + svgFile.getAbsolutePath() );

            return svgFile;
        }
        catch( FileHelperException e ){

            throw new SvgGeneratorException( "Could not generate SVG", e );
        }
    }


    private List<List<ColumnElement>> getColumns(
            List<ColumnElement> columnElements ){

        String columnElementText;
        String svgTag;
        int columnElementHeight;
        int lastHeight = 0;

        List<List<ColumnElement>> columns =
            new ArrayList<List<ColumnElement>>();

        List<ColumnElement> currentColumn = new ArrayList<ColumnElement>();
        columns.add( currentColumn );

        for ( ColumnElement columnElement: columnElements ){

            columnElementText = columnElement.getText();
            svgTag = getSvgOpeningTag( columnElementText );
            columnElementHeight = getHeight( svgTag );

            lastHeight =
                lastHeight + columnElementHeight +
                    verticalSpaceBetweenSvgsInPixels;

            if ( lastHeight > maxHeightColumnInPixels ){

                currentColumn = new ArrayList<ColumnElement>();
                columns.add( currentColumn );

                lastHeight =
                    columnElementHeight + verticalSpaceBetweenSvgsInPixels;
            }

            currentColumn.add( columnElement );
        }

        return columns;
    }


    private void addOpeningTagForColumnFile( StringWriter columnText ){

        columnText.append(
            "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"" );

        columnText.append( "width=\"" ).append( widthColumnInPixels + "" );
        columnText.append( "\" " );
        columnText.append( "height=\"");
        columnText.append( maxHeightColumnInPixels + "" );
        columnText.append( "px\">\n" );
        columnText.append( "<g>\n" );
    }


    private void addClosingTagForColumnFile( StringWriter columnText ){

        columnText.append( "</g>\n" );
        columnText.append( "</svg>\n" );
    }


    private Collection<File> generateAdvertSection(
            AdvertSection advertSection, File expandedZipDirectoryFile,
                File svgDirectoryToGenerateSvgFilesTo,
                    String advertType ) throws SvgGeneratorException {
        try {
            Collection<File> svgFiles = new ArrayList<File>();

            List<ColumnElement> columnElements = new ArrayList<ColumnElement>();
            columnElements.add( advertSection );

            List<Advert> adverts =
                advertSection.getAdvertsByType( advertType );

            columnElements.addAll( adverts );

            String text;
            String svgTag;
            int columnElementHeight;

            for ( ColumnElement columnElement : columnElements ){

                text =
                    populatePlaceHolders(
                            fileHelper, columnElement.getSvgTemplateFile(),
                                columnElement.getPropertyMap() );

                columnElement.setText( text );
                svgTag = getSvgOpeningTag( text );
                columnElementHeight = getHeight( svgTag );
                columnElement.setHeight( columnElementHeight );
            }

            List<List<ColumnElement>> columns = getColumns( columnElements );

            int columnCount = 0;
            int yPosition;
            Advert advert;
            StringWriter columnText;
            File svgFile;

            for ( List<ColumnElement> column : columns ){

                columnText = new StringWriter();

                addOpeningTagForColumnFile( columnText );

                yPosition = 0;

                columnCount++;

                for ( ColumnElement columnElement : column ){

                    text =
                        columnElement.getText().replaceAll(
                                "#y#", yPosition + "" );

                    columnElement.setText( text );
                    columnElementHeight = columnElement.getHeight();

                    yPosition =
                        yPosition + columnElementHeight +
                            verticalSpaceBetweenSvgsInPixels;

                    if ( columnElement instanceof Advert ){

                        advert = ( Advert )columnElement;

                        copyImageToSvgDirectoryToGenerateSvgFilesTo(
                                advert, advertSection, advertType,
                                    expandedZipDirectoryFile,
                                        svgDirectoryToGenerateSvgFilesTo );
                    }

                    columnText.append( columnElement.getText() );
                }

                addClosingTagForColumnFile( columnText );

                svgFile = createSvgFile(
                        columnText.toString(), columnCount,
                             svgDirectoryToGenerateSvgFilesTo,
                              advertSection.getSectionNameSuitableForFileSystem(),
                                  advertType );

                svgFiles.add( svgFile );
            }

            return svgFiles;
        }
        catch( IOException e ){

            throw new SvgGeneratorException(
                    "Could not generate Svg files", e );
        }
    }


    @Autowired
    public SvgGeneratorImpl( FileHelper fileHelper ){

        this.fileHelper = fileHelper;
    }



    /* (non-Javadoc)
     * @see com.javaspeak.bigduka.svg.generator.SvgGenerator#
     *         generateAdvertSection(com.javaspeak.bigduka.svg.generator.
     *             AdvertSection, java.io.File, java.io.File)
     */
    public Collection<File> generateAdvertSection(
        AdvertSection advertSection, File expandedZipDirectoryFile,
            File svgDirectoryToGenerateSvgFilesTo )
                throws SvgGeneratorException {

        Set<String> advertTypes = advertSection.getAdvertTypes();

        Collection<File> svgFiles = new ArrayList<File>();

        for ( String advertType : advertTypes ){

            svgFiles.addAll(
                generateAdvertSection(
                    advertSection, expandedZipDirectoryFile,
                        svgDirectoryToGenerateSvgFilesTo, advertType ) );
        }

        return svgFiles;
    }
}
