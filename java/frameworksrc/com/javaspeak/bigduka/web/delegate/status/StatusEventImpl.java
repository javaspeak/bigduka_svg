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

/**
 * @author John Dickerson
 */
public class StatusEventImpl implements StatusEvent {

	private String message;
	private StatusEventMessageTypeEnum statusEventMessageTypeEnum;
	
	
	public StatusEventImpl( String message, 
			StatusEventMessageTypeEnum statusEventMessageTypeEnum ){
		
		this.message = message;
		this.statusEventMessageTypeEnum = statusEventMessageTypeEnum;
	}
	
	
	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.web.delegate.staticpage.
	 * 		StatusEvent#getMessage()
	 */
	public String getMessage() {
		
		String messageToReturn = "";
		
		if ( statusEventMessageTypeEnum == StatusEventMessageTypeEnum.DEBUG ){
		
			messageToReturn = "DEBUG : " + message;
		}
		else if ( statusEventMessageTypeEnum == 
			StatusEventMessageTypeEnum.WARN ){
		
			messageToReturn = 
				"<font color='blue'>WARNING : " + message + "</font>";
		}
		else if ( statusEventMessageTypeEnum == 
			StatusEventMessageTypeEnum.ERROR ){
			
			messageToReturn = 
				"<font color='red'>ERROR : " + message + "</font>";
		}
		
		return messageToReturn;
	}
	

	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.web.delegate.staticpage.
	 * 		StatusEvent#getStatusEventMessageTypeEnum()
	 */
	public StatusEventMessageTypeEnum getStatusEventMessageTypeEnum() {
		
		return statusEventMessageTypeEnum;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage( String message ) {
		
		this.message = message;
	}
}
