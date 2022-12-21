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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.javaspeak.bigduka.web.delegate.status.StatusListener;

/**
 * Interface modelling common file operations
 *
 * @author John Dickerson
 */
public interface FileHelper {

    /**
     * Gets the file extension of a file with or without the dot
     *
     * @param file File to get file extension from
     * @param hasDot whether file extension should be returned with a
     * dot or not
     *
     * @return file extension
     */
    public String getExtension( File file, boolean hasDot );


    /**
     * Checks whether a file is in a directory or not
     *
     * @param file file to look for
     *
     * @param dir directory to look for file in
     *
     * @return true if find file
     */
    public boolean isFileInDir( File file, File dir );


    /**
     * Get File name without file extension
     *
     * @param fileName name of file
     *
     * @return filename without file extension
     */
    public String getFileWithoutFileExtension( String fileName );


    /**
     * Get a unique file name. Uses the base name, fileNameMinusExtension, and
     * checks whether any files of that name exist in the directory. If they do,
     * the file name has a _z added to it before the file extension where z is
     * a number. Spaces are also converted to underscores.
     *
     * @param fileNameMinusExtension file name without file extension
     *
     * @param dir directory where file name should be unique
     *
     * @param extension file extension
     *
     * @return unique file name
     */
    public String getUniqueFileName( String fileNameMinusExtension, String dir,
            String extension );


    /**
     * Copy char file
     *
     * @param sourceFile file to copy
     *
     * @param destinationFile destination file to copy to
     *
     * @throws FileHelperException
     */
    public void copyCharFile( File sourceFile, File destinationFile)
            throws FileHelperException;


    /**
     * Copy input stream to destination file
     *
     * @param inputStream input stream to copy from
     *
     * @param destinationFile file to copy to
     *
     * @throws FileHelperException
     */
    public void copyInputStream( InputStream inputStream, File destinationFile)
            throws FileHelperException;


    /**
     * Copy a binary file
     *
     * @param sourceFile file to copy from
     *
     * @param destinationFile file to copy to
     *
     * @throws FileHelperException
     */
    public void copyBinaryFile( File sourceFile, File destinationFile )
            throws FileHelperException;


    /**
     * Copy a directory to another directory
     *
     * @param sourceDir Directory to copy from
     *
     * @param destinationDir directory to copy to
     *
     * @return new directory
     *
     * @throws FileHelperException
     */
    public File copyDirectory( File sourceDir, File destinationDir )
            throws FileHelperException;


    /**
     * Copy the directory and its contents to the destination directory
     *
     * @param sourceDir source directory to copy directory and its contents from
     *
     * @param destinationDir destination directory to copy directory and
     * its contents to
     *
     * @throws FileHelperException
     */
    public void copyDirectoryRecursively( File sourceDir, File destinationDir)
            throws FileHelperException;


    /**
     * Copy directory and its contents to a destination directory but in so
     * doing rename top directory
     *
     * @param sourceDir source directory to copy directory and contents from
     *
     * @param destinationDir destination directory to copy source directory to
     *
     * @param newDirName name of top when directory copy source directories and
     * content to
     *
     * @throws FileHelperException
     */
    public void copyDirRecursivelyAndRenameTopDir(File sourceDir,
            File destinationDir, String newDirName) throws FileHelperException;


    /**
     * Delete directory and its contents
     *
     * @param file file and its contents to delete
     *
     * @throws FileHelperException
     */
    public void deleteRecursively( File file ) throws FileHelperException;


    /**
     * Delete directory and its contents
     *
     * @param file file and its contents to delete
     *
     * @param statusListener listener to fire StatusEvents to
     *
     * @throws FileHelperException
     */
    public void deleteRecursively(
    		File file, StatusListener statusListener )
    			throws FileHelperException;


    /**
     * Copy File and its contents if directory to a destination directory
     *
     * @param sourceFile name of source file or directory
     *
     * @param destinationDir directory to copy file or directory and its
     * contents to
     *
     * @throws FileHelperException
     */
    public void copyRecursively(File sourceFile, File destinationDir)
            throws FileHelperException;


    /**
     * Gets file name stripped of path and file extension
     *
     * @param file file to get file name stripped of path and file extension
     * @return
     */
    public String getNameStrippedOfPathAndExtension( File file );



    /**
     * Get file na me stripped of Path
     *
     * @param fileName file to bget teh file name stripped of
     * @return
     */
    public String getNameStrippedOfPath( String fileName );


    /**
     * Gets a list of child files that match the specified set of file
     * extensions
     *
     * @param parentFile directory to look for child files that match the file
     * extensions in the set
     *
     * @param fileTypeSet the set of file extensions to match against
     *
     * @return list of matching files.
     */
    public List<File> getChildFileList(
            File parentFile, Set<String> fileTypeSet );


    /**
     * Chooses a random file
     *
     * @param childFileList list of files to choose a random one from
     *
     * @return
     *
     * @throws FileHelperException
     */
    public File chooseRandomFile( List<File> childFileList )
        throws FileHelperException;


    /**
     * Reads Text File
     *
     * @param textFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String readTextFile( File textFile )
    	throws FileNotFoundException, IOException;


    /**
     * Write Text file
     *
     * @param textFile
     * @param textToWrite
     */
    public void writeTextFile( File textFile, String textToWrite )
    	throws FileHelperException;


    /**
     * Replaces illegal caharacters for directory name with underscore
     *
     * @param segmentName
     * @return
     */
    public String replaceIllegalCharactersForDirectoryName(
			String fileName );



    /**
     * Counts number of files under directory and all its children directories
     * Excludes directories in the count
     *
     * @param directory directory to count files.
     */
    public long countFiles( File directory, long startingCount );
}