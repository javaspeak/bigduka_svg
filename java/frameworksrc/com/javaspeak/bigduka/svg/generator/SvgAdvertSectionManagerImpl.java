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
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
public class SvgAdvertSectionManagerImpl
	implements SvgAdvertSectionManager, PublishStatusEvent {

	private SortedSet<AdvertSection> advertSections;
	private SvgGenerator svgGenerator;

	private List<StatusListener> statusListeners =
		new ArrayList<StatusListener>();


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.svg.generator.SvgAdvertSectionManager#
	 * 		initialize(com.javaspeak.bigduka.svg.generator.SvgDataRetriever,
	 * 			com.javaspeak.bigduka.svg.generator.SvgGenerator)
	 */
	public void initialize( SvgDataRetriever svgDataRetriever,
		SvgGenerator svgGenerator )
				throws SvgDataRetrieverException {

		this.svgGenerator = svgGenerator;

		advertSections =
			svgDataRetriever.getAdvertSections();
	}


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.svg.generator.SvgAdvertSectionManager#
	 * 		generateAdvertSections(java.io.File, java.io.File)
	 */
	public SortedSet<File> generateAdvertSections(
		File expandedZipDirectoryFile, File svgDirectoryToGenerateSvgFilesTo )
			throws SvgGeneratorException {

		SortedSet<File> svgFiles = new TreeSet<File>();

		Collection<File> svgFilesToAdd;

		for ( AdvertSection advertSection: advertSections ){

			svgFilesToAdd =
				svgGenerator.generateAdvertSection(
						advertSection, expandedZipDirectoryFile,
							svgDirectoryToGenerateSvgFilesTo );

			svgFiles.addAll( svgFilesToAdd );
		}

		return svgFiles;
	}


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.svg.generator.SvgAdvertSectionManager#
	 * 		addStatusListener(
	 * 			com.javaspeak.bigduka.svg.generator.StatusListener)
	 */
	public void addStatusListener( StatusListener statusListener ) {
		statusListeners.add( statusListener );
	}


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.svg.generator.PublishStatusEvent#
	 * 		publishStatusEvet(com.javaspeak.bigduka.svg.generator.StatusEvent)
	 */
	public void publishStatusEvent( StatusEvent statusEvent ) {

		for ( StatusListener statusListener : statusListeners ){

			statusListener.statusEventOccured( statusEvent );
		}
	}
}
