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
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Start implements StatusListener {

	public Logger logger = Logger.getLogger( Start.class );

	private SvgAdvertSectionManager svgAdvertSectionManager;
	private SvgDataRetriever svgDataRetriever;
	private SvgGenerator svgGenerator;


	private static void showUsage(){

		StringWriter writer = new StringWriter();
		writer.append( "usage:java Start com.javaspeak.bigduka.svg." );
		writer.append( "generator.Start <expandedZipDirectory> " );
		writer.append( "<directoryToGenerateSvgTo>" );

		System.out.println( writer.toString() );
	}


	@Autowired
	public Start( SvgAdvertSectionManager svgAdvertSectionManager,
		@Qualifier( "defaultSvgDataRetriever" )SvgDataRetriever svgDataRetriever,
			@Qualifier( "defaultSvgGenerator" )SvgGenerator svgGenerator ){

		this.svgAdvertSectionManager = svgAdvertSectionManager;
		this.svgDataRetriever = svgDataRetriever;
		this.svgGenerator = svgGenerator;

		this.svgAdvertSectionManager.addStatusListener( this );
	}


	public SortedSet<File> start( File expandedZipDirectory,
			File svgDirectoryToGenerateSvgFilesTo )
				throws SvgDataRetrieverException, SvgGeneratorException {

		logger.debug( "About to generate files from " +
				svgDirectoryToGenerateSvgFilesTo.getAbsolutePath() );

		SvgDataRetrieverImpl svgDataRetrieverImpl
			= ( SvgDataRetrieverImpl )svgDataRetriever;

		svgDataRetrieverImpl.setExpandedZipDirectory( expandedZipDirectory );

		svgAdvertSectionManager.initialize(
				svgDataRetriever, svgGenerator );

		SortedSet<File> svgFiles =
			svgAdvertSectionManager.generateAdvertSections(
					expandedZipDirectory, svgDirectoryToGenerateSvgFilesTo );

		return svgFiles;
	}


	public SvgDataRetriever getSvgDataRetriever() {

		return svgDataRetriever;
	}


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.svg.generator.StatusListener#
	 * 		statusEventOccured(com.javaspeak.bigduka.svg.generator.StatusEvent)
	 */
	public void statusEventOccured( StatusEvent statusEvent ) {

		logger.debug( statusEvent );
	}


	public static void main( String args[] )
		throws SvgDataRetrieverException, SvgGeneratorException {

		BasicConfigurator.configure();

		System.out.println( "Loading up Spring context" );

		ClassPathXmlApplicationContext context =
			new ClassPathXmlApplicationContext(
					"/spring/applicationContext.xml" );
		Start start =
			( Start )context.getBean( "start" );

		System.out.println( "Finished Loading up Spring context" );

		if ( args.length != 2 ){

			showUsage();

			System.exit( 0 );
		}

		String expandedZipDirectoryPath = args[ 0 ];

		File expandedZipDirectory = new File( expandedZipDirectoryPath );

		if ( ! expandedZipDirectory.isDirectory() ){

			throw new RuntimeException(
				expandedZipDirectory.getAbsolutePath() +
					" is not found" );
		}

		String directoryToGenerateSvgToPath = args[ 1 ];

		File directoryToGenerateSvgTo =
			new File( directoryToGenerateSvgToPath );

		if ( ! directoryToGenerateSvgTo.isDirectory() ){

			throw new RuntimeException(
					directoryToGenerateSvgTo.getAbsolutePath() +
					" is not found" );
		}

		SimpleDateFormat timeStampFormat =
			new SimpleDateFormat( "yyyyMMdd_HHmmssSSS" );

		String timeStamp = timeStampFormat.format( new Date() );

		File timeStampDirectory =
			new File( directoryToGenerateSvgTo, timeStamp );

		if ( ! timeStampDirectory.mkdir() ){

			throw new RuntimeException(
				"Could not make directory, " +
					timeStampDirectory.getAbsolutePath() );
		}

		start.start( expandedZipDirectory, timeStampDirectory );
	}
}
