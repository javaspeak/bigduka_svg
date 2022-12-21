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

import java.util.SortedSet;

public interface SvgDataRetriever {

	public SortedSet<AdvertSection> getAdvertSections()
			throws SvgDataRetrieverException;

	public SortedSet<AdvertSection> getCachedAdvertSections();
}
