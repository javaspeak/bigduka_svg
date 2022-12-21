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
package com.javaspeak.bigduka.web.delegate.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.javaspeak.bigduka.web.delegate.status.StatusEventImpl;
import com.javaspeak.bigduka.web.delegate.status.StatusEventMessageTypeEnum;
import com.javaspeak.bigduka.web.delegate.status.StatusListener;



/**
 * Interface modelling common file operations
 *
 * @author John Dickerson
 */
@Component
public class FileHelperImpl implements FileHelper {

    protected Logger logger = Logger.getLogger( FileHelperImpl.class );

    private String fileSeparator;


    public FileHelperImpl() {
        super();
        initialize();
    }


    /**
     * Initializes FileHelper
     */
    private void initialize() {

        fileSeparator = System.getProperty( "file.separator" );
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#getExtension(java.io.File, boolean)
     */
    public String getExtension( File file, boolean hasDot ) {

        String fileStr = file.getName();

        int indexOfDot;
        int fromIndex = 0;

        String extension = null;

        while ( ( indexOfDot = fileStr.indexOf( ".", fromIndex ) ) != -1 ) {

            extension = fileStr.substring( indexOfDot );

            fromIndex = indexOfDot + 1;
        }

        if ( ! hasDot ){

            if ( ( indexOfDot = extension.indexOf( "." ) ) != -1 ){

                return extension.substring( indexOfDot + 1 );
            }
        }

        return extension;
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#isFileInDir(java.io.File, java.io.File)
     */
    public boolean isFileInDir( File file, File dir ) {

        File[] files = dir.listFiles();

        for ( int i=0; i<files.length; i++ ) {

            if ( files[ i ].equals( file ) ) {

                return true;
            }
        }

        return false;
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#getFileWithoutFileExtension(java.lang.String)
     */
    public String getFileWithoutFileExtension( String fileName ) {

        int index = fileName.lastIndexOf( "." );

        return fileName.substring( 0, index );
    }


	/* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#getUniqueFileName(java.lang.String,
     *                  java.lang.String, java.lang.String)
     */
    public String getUniqueFileName(
            String fileNameMinusExtension, String dir, String extension ) {

        String fileNameMinusExtensionWithSpacesReplaced
        	= fileNameMinusExtension.replaceAll( "[ ]+", "_" );

        String fileToSaveStr
        	= dir + fileSeparator
        		+ fileNameMinusExtensionWithSpacesReplaced + extension;

        File fileToSave = new File( fileToSaveStr );

        int i = 1;

        while ( fileToSave.exists() ) {

            i++;

            fileToSaveStr
            	= dir + fileSeparator
            		+ fileNameMinusExtensionWithSpacesReplaced
            			+ "_" + i + extension;

            fileToSave = new File( fileToSaveStr );
        }

        return fileToSave.getAbsolutePath();
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyCharFile(java.io.File, java.io.File)
     */
    public void copyCharFile(
            File sourceFile, File destinationFile )
    			throws FileHelperException {

        Reader reader = null;
        Writer writer = null;

        try {
	        if ( sourceFile.exists()
	                && sourceFile.canRead()
	                	&& sourceFile.isFile() ) {

	            reader = new FileReader( sourceFile );
	            writer = new FileWriter( destinationFile );

	            char[] charArray = new char[ 1024 ];

	            int charsRead;

	            while ( ( charsRead = reader.read( charArray ) ) != -1 ) {

	                writer.write( charArray, 0, charsRead );
		    }
	        }
	        else {

	            StringBuffer sb =
	                new StringBuffer( "Could not copy file, " );

	            sb.append( sourceFile.getAbsolutePath() );
	            sb.append( ", to, " );
	            sb.append( destinationFile.getAbsolutePath() );
	            sb.append( ", because it does not exist or it is " );
	            sb.append( "not readable or it is not a file" );

	            logger.error( sb.toString() );

	            throw new FileHelperException( sb.toString() );
	        }
        }
        catch( FileNotFoundException e ) {

            StringBuffer sb = new StringBuffer( "Could not copy file, " );
            sb.append( sourceFile.getAbsolutePath() );
            sb.append( ", to, " );
            sb.append( destinationFile.getAbsolutePath() );

            logger.error( sb.toString(), e );

            throw new FileHelperException( sb.toString(), e );
        }
        catch( IOException e ) {

            StringBuffer sb = new StringBuffer( "Could not copy file, " );
            sb.append( sourceFile.getAbsolutePath() );
            sb.append( ", to, " );
            sb.append( destinationFile.getAbsolutePath() );

            logger.error( sb.toString(), e );

            throw new FileHelperException( sb.toString(), e );

        }
        finally {

            try {
                if ( reader != null ) {

                    reader.close();
                }

                if ( writer != null ) {

                    writer.close();
                }
            }
            catch( Exception e ) {}
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyInputStream(java.io.InputStream, java.io.File)
     */
    public void copyInputStream(
            InputStream inputStream, File destinationFile )
		throws FileHelperException{

        OutputStream outputStream = null;

        try {
	        outputStream = new FileOutputStream( destinationFile );

	        int numberBytesRead;

	        byte[] byteArray = new byte[ 1024 ];

	        while ( ( numberBytesRead =
	            inputStream.read( byteArray ) ) != -1 ) {

	             outputStream.write( byteArray, 0, numberBytesRead );
	        }

            inputStream.close();
            outputStream.close();

        }
        catch( FileNotFoundException e ) {

            StringBuffer sb =
                new StringBuffer( "Could not copy inputstrean, " );

            sb.append( ", to, " );
            sb.append( destinationFile.getAbsolutePath() );

            logger.error( sb.toString(), e );

            throw new FileHelperException( sb.toString(), e );
        }
        catch( IOException e ) {

            StringBuffer sb =
                    new StringBuffer( "Could not copy inputstrean, " );

            sb.append( ", to, " );
            sb.append( destinationFile.getAbsolutePath() );

            logger.error( sb.toString(), e );

            throw new FileHelperException( sb.toString(), e );
        }
        finally {

            try {
                if ( inputStream != null ) {

                    inputStream.close();
                }

                if ( outputStream !=  null ) {

                    outputStream.close();
                }
            }
            catch( Exception e ) {}
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyBinaryFile(java.io.File, java.io.File)
     */
    public void copyBinaryFile( File sourceFile, File destinationFile )
    	throws FileHelperException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            if ( sourceFile.exists() && sourceFile.canRead()
    	               && sourceFile.isFile() ) {

                inputStream = new  FileInputStream( sourceFile );
	        outputStream = new FileOutputStream( destinationFile );

	        byte[] byteArray = new byte[ 1024 ];

	        int numberBytesRead;

	        while ( ( numberBytesRead =
	            inputStream.read( byteArray ) ) != -1 ) {

	            outputStream.write( byteArray, 0, numberBytesRead );
	        }

                inputStream.close();
                outputStream.close();
	    }
            else {

                StringBuffer sb = new StringBuffer( "Could not copy file, " );
    	        sb.append( sourceFile.getAbsolutePath() );
    	        sb.append( ", to, " );
    	        sb.append( destinationFile.getAbsolutePath() );
    	        sb.append( ", because it does not exist or it is " );
    	        sb.append( "not readable or it is not a file" );

	        logger.error( sb.toString() );

	        throw new FileHelperException( sb.toString() );
	    }
        }
        catch( FileNotFoundException e ) {

            StringBuffer sb = new StringBuffer( "Could not copy file, " );
            sb.append( sourceFile.getAbsolutePath() );
            sb.append( ", to, " );
            sb.append( destinationFile.getAbsolutePath() );

            logger.error( sb.toString(), e );

            throw new FileHelperException( sb.toString(), e );
        }
        catch( IOException e ) {

            StringBuffer sb = new StringBuffer( "Could not copy file, " );
            sb.append( sourceFile.getAbsolutePath() );
            sb.append( ", to, " );
            sb.append( destinationFile.getAbsolutePath() );

            logger.error( sb.toString(), e );

            throw new FileHelperException( sb.toString(), e );
        }
        finally {

            try {
                if ( inputStream != null ) {

                    inputStream.close();
                }

                if ( outputStream !=  null ) {

                    outputStream.close();
                }
            }
            catch( Exception e ) {}
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyDirectory(java.io.File, java.io.File)
     */
    public File copyDirectory( File sourceDir, File destinationDir )
    	throws FileHelperException {

        boolean isSuccessfull = false;
        File directoryToCreate = null;

        if ( sourceDir.exists() && sourceDir.isDirectory()
                && sourceDir.canRead() ) {

            if ( destinationDir.exists() && destinationDir.isDirectory()
                    	&& destinationDir.canWrite() ) {

                String directoryToCreateName
                	= destinationDir.getAbsolutePath() + fileSeparator
                		+ sourceDir.getName();

                directoryToCreate = new File( directoryToCreateName );

                isSuccessfull = directoryToCreate.mkdir();
            }
        }

        if ( isSuccessfull ) {

            return directoryToCreate;
        }
        else {

            StringBuffer sb = new StringBuffer( "Could not create directory: " );
            sb.append( directoryToCreate.getAbsolutePath() );

            logger.error( sb.toString() );

            throw new FileHelperException( sb.toString() );
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyDirectoryRecursively(java.io.File, java.io.File)
     */
    public void copyDirectoryRecursively(
            File sourceDir, File destinationDir )
    	        throws FileHelperException {

        if ( sourceDir.exists() && sourceDir.isDirectory()
                && sourceDir.canRead() ) {

            if ( destinationDir.exists() && destinationDir.isDirectory()
                    && destinationDir.canWrite() ) {

                copyRecursively( sourceDir, destinationDir );
            }
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyDirRecursivelyAndRenameTopDir(
     *                  java.io.File, java.io.File, java.lang.String)
     */
    public void copyDirRecursivelyAndRenameTopDir(
            File sourceDir, File destinationDir, String newDirName )
    			throws FileHelperException {

        String destinationDirName;
        File newDestinationDir;
        File[] childrenFile = sourceDir.listFiles();

        if ( sourceDir.exists() && sourceDir.isDirectory()
                && sourceDir.canRead() ) {
            if ( destinationDir.exists() && destinationDir.isDirectory()
                    && destinationDir.canWrite() ) {

                destinationDirName = destinationDir + fileSeparator
                	+ newDirName;
                newDestinationDir = new File( destinationDirName );
                newDestinationDir.mkdir();
                File destinationFile;

                for ( int i=0; i<childrenFile.length; i++ ) {

                    if ( childrenFile[ i ].isFile() ) {

                        destinationFile
                        	= new File( newDestinationDir + fileSeparator
                        	        + childrenFile[ i ].getName() );

                        copyBinaryFile(
                                childrenFile[ i ], destinationFile );
                    }
                    else if ( childrenFile[ i ].isDirectory() ) {

                        copyRecursively( childrenFile[ i ],
                                newDestinationDir );
                    }
                }
            }
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#deleteRecursively(java.io.File)
     */
    public void deleteRecursively( File file ) throws FileHelperException {

        if ( file.isDirectory() ) {

            File[] childrenFileArray = file.listFiles();

            for ( int i=0; i<childrenFileArray.length; i++ ) {

                if ( childrenFileArray[ i ].isFile() ) {

                    childrenFileArray[ i ].delete();
                }
                else {

                    deleteRecursively( childrenFileArray[ i ] );
                }
            }

	    // directory should now be empty so we can delete it
	    if ( ! file.delete() ) {

	        StringBuffer sb = new StringBuffer( "Could not delete file: " );
	        sb.append( file.getAbsolutePath() );

	        logger.error( sb.toString() );

	        throw new FileHelperException( sb.toString() );
	    }
        }
        else {
            if ( ! file.delete() ) {

                StringBuffer sb =
                    new StringBuffer( "Could not delete file: " );

                sb.append( file.getAbsolutePath() );

	        logger.error( sb.toString() );

	        throw new FileHelperException( sb.toString() );
            }
        }
    }


    /* (non-Javadoc)
	 * @see com.javaspeak.bigduka.web.delegate.file.
	 * 		FileHelper#deleteRecursively(
	 * 			java.io.File,
	 * 				com.javaspeak.bigduka.web.delegate.status.StatusListener)
	 */
	public void deleteRecursively( File file, StatusListener statusListener )
			throws FileHelperException {

		if ( file.isDirectory() ) {

            File[] childrenFileArray = file.listFiles();

            for ( int i=0; i<childrenFileArray.length; i++ ) {

                if ( childrenFileArray[ i ].isFile() ) {

                    childrenFileArray[ i ].delete();

                    String message =
            			"Deleted file, " +
            				childrenFileArray[ i ].getAbsolutePath();

            		statusListener.statusEventOccured(
            				new StatusEventImpl( message,
            						StatusEventMessageTypeEnum.DEBUG ) );

            		logger.debug( message );
                }
                else {

                    deleteRecursively( childrenFileArray[ i ] );
                }
            }

		    // directory should now be empty so we can delete it
		    if ( file.delete() ) {

		    	String message =
        			"Deleted directory: " + file.getAbsolutePath();

        		statusListener.statusEventOccured(
        				new StatusEventImpl( message,
        						StatusEventMessageTypeEnum.DEBUG ) );
		    }
		    else {

		    	String message =
        			"Could not delete directory: " + file.getAbsolutePath();

        		statusListener.statusEventOccured(
        				new StatusEventImpl( message,
        						StatusEventMessageTypeEnum.ERROR ) );

        		logger.error( message );

		        throw new FileHelperException( message );
		    }

        }
        else {
            if ( file.delete() ) {

            	String message =
        			"Deleted file: " + file.getAbsolutePath();

        		statusListener.statusEventOccured(
        				new StatusEventImpl( message,
        						StatusEventMessageTypeEnum.DEBUG ) );

        		logger.debug( message );
            }
            else {

            	String message =
        			"Could not delete file: " + file.getAbsolutePath();

        		statusListener.statusEventOccured(
        				new StatusEventImpl( message,
        						StatusEventMessageTypeEnum.ERROR ) );

        		logger.error( message );

		        throw new FileHelperException( message );
            }
        }
	}


	/* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#copyRecursively(java.io.File, java.io.File)
     */
    public void copyRecursively( File sourceFile, File destinationDir )
    	throws FileHelperException {

        if ( sourceFile.isFile() ) {

            String sourceFileNameWithoutPath = sourceFile.getName();

            String destinationFileName
                = destinationDir.getAbsolutePath() + fileSeparator +
                     sourceFileNameWithoutPath;

            File destinationFile = new File( destinationFileName );

            copyBinaryFile(
                    sourceFile, destinationFile );
        }
        else {

            File dirCreated = copyDirectory( sourceFile, destinationDir );

            File[] childrenFile = sourceFile.listFiles();

            for ( int i=0; i<childrenFile.length; i++ ) {

                copyRecursively( childrenFile[ i ], dirCreated );
            }
        }
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#getNameStrippedOfPathAndExtension(java.io.File)
     */
    public String getNameStrippedOfPathAndExtension( File file ) {

        String name = file.getName();

        int indexOfExtension = name.indexOf( "." );

        String nameStrippedOfPathAndExtension;

        if ( indexOfExtension != -1 ) {

            nameStrippedOfPathAndExtension
            	= name.substring( 0, indexOfExtension );
        }
        else {
            nameStrippedOfPathAndExtension = name;
        }

        return nameStrippedOfPathAndExtension;
    }


    /* (non-Javadoc)
     * @see com.javaspeak.bigduka.web.delegate.file.FileHelper#
     * 		getNameStrippedOfPath(java.lang.String)
     */
	public String getNameStrippedOfPath(String fileName) {

        File file = new File( fileName );

        return file.getName();
	}


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#getChildFileList(java.io.File, java.util.Set)
     */
    public List<File> getChildFileList(
            File parentFile, Set<String> fileTypeSet ){

        File[] fileArray = parentFile.listFiles();

        List<File> childFileList = new ArrayList<File>();

        String fileExtension;

        for ( File file : fileArray ){

            fileExtension = getExtension( file, false );

            if ( fileTypeSet.contains( fileExtension ) ) {

                childFileList.add( file );
            }
        }

        return childFileList;
    }


    /* (non-Javadoc)
     * @see com.javaspeak.languagetutor.utilities.filehelper.
     *          FileHelper#chooseRandomFile(java.util.List)
     */
    public File chooseRandomFile( List<File> childFileList ) {

        int numberFiles = childFileList.size();

        if ( numberFiles > 0 ) {

            Random random = new Random( ( new Date() ).getTime() );

            int index = random.nextInt( numberFiles );

            return childFileList.get( index );
        }
        else {

            return null;
        }
    }


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.web.delegate.file.FileHelper#
	 * 		readTextFile(java.io.File)
	 */
	public String readTextFile( File textFile )
		throws FileNotFoundException, IOException {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader( textFile );

			bufferedReader = new BufferedReader( fileReader );

			String line;
			StringBuffer sb = new StringBuffer();

			while ( ( line = bufferedReader.readLine() ) != null ){

				sb.append( line ).append( "\n" );
			}

			String string = sb.toString();
			return string;
		}
		finally {

			if ( bufferedReader != null ){

				try {
					bufferedReader.close();
				}
				catch( IOException e ){
					// do nothing
				}
			}
		}
	}


	/* (non-Javadoc)
	 * @see com.javaspeak.bigduka.web.delegate.file.FileHelper#writeTextFile(
	 * 	java.io.File, java.lang.String)
	 */
	public void writeTextFile( File textFile, String textToWrite )
		throws FileHelperException {

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter( textFile );

			fileWriter.write( textToWrite );
		}
		catch( IOException e ){

			throw new FileHelperException(
				"Could not write file, " +
					textFile.getAbsolutePath(), e );
		}
		finally {

			if ( fileWriter != null ){

				try {
					fileWriter.close();
				}
				catch( IOException e ){
					// do nothing
				}
			}
		}
	}


	public String replaceIllegalCharactersForDirectoryName(
			String path ){

		String[] illegalCharacters =
			{ "\\/", "\\\\", "\\?", "\\%", "\\*", "\\:", "\\|", "\"",
				"\\<", "\\>", "\\.", " " };

		for ( String illegalCharacter : illegalCharacters ){

			path = path.replaceAll( illegalCharacter , "_" );
		}

		return path;
	}



	public long countFiles( File directory, long startingCount ) {

		long count = startingCount;

		for ( File file : directory.listFiles() ){

			if ( file.isFile() ){

				count = count + 1;
			}
			else {

				count = count + countFiles( file, count );
			}
		}

		return count;
	}


	/**
     * @param args
     */
    public static void main( String args[] ) {

        FileHelper fileHelper = new FileHelperImpl();

        String extension
        	= fileHelper.getExtension(
        	        new File( "c:\\a\\a.b.c.d.log" ), true );

        System.out.println( "extension = " + extension );

        String directory = "a/b\\c?d%e*f:g|h\"i<j>k.l m";

        directory =
        	fileHelper.replaceIllegalCharactersForDirectoryName( directory );

        System.out.println( "directory = " + directory );
    }
}