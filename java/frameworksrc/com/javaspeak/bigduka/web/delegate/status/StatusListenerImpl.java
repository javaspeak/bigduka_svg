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
package com.javaspeak.bigduka.web.delegate.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;


/**
 * @author John Dickerson
 */
public class StatusListenerImpl implements StatusListener {
	
	private Logger logger = 
		Logger.getLogger( StatusListenerImpl.class );
	
	private SimpleDateFormat timeStampFormat = 
		new SimpleDateFormat( "EEEEE, dd MMMM yyyy, HH:mm:ss SSS" ); 
	
	private LinkedBlockingQueue<StatusEvent> statusEventLoggingQueue;
	
	
	public StatusListenerImpl( 
			LinkedBlockingQueue<StatusEvent> statusEventLoggingQueue ){
		
		this.statusEventLoggingQueue = statusEventLoggingQueue;
	}
	
	
	public void statusEventOccured( StatusEvent statusEvent ) {
		
		String message = 
			timeStampFormat.format( new Date() ) + 
				" : " + statusEvent.getMessage();
		
		statusEvent.setMessage( message );
		
		if ( ! statusEventLoggingQueue.offer( statusEvent ) ){
			
			synchronized ( this ) {
			
				// Retrieves and removes the head of this queue, or returns
				// null if this queue is empty.
				statusEventLoggingQueue.poll();
				
				// Inserts the specified element at the tail of this queue 
				// if it is possible to do so immediately without exceeding 
				// the queue's capacity, returning true upon success and false 
				if ( ! statusEventLoggingQueue.offer( statusEvent ) ){
				
					logger.debug( 
						"Could not add statusEvent to queue as queue is full" );
					
				}
			}
		}
	}
}
