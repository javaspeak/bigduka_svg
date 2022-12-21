package com.javaspeak.bigduka.svg.generator;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/applicationContext.xml" })
public class StartTest {

    /**
    public static Logger logger =
        Logger.getLogger( StartTest.class );

    // autowired ===============================================================
    @Autowired
    private Start start;
    // end autowired ===========================================================

    private static SortedSet<File> svgFiles;
    private static SvgDataRetriever svgDataRetriever;

    private void deleteRecursively( File file ) {

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

            throw new RuntimeException( sb.toString() );
        }
        }
        else {
            if ( ! file.delete() ) {

                StringBuffer sb =
                    new StringBuffer( "Could not delete file: " );

                sb.append( file.getAbsolutePath() );

            logger.error( sb.toString() );

            throw new RuntimeException( sb.toString() );
            }
        }
    }


    private File deleteSvgTempDirectoryIfExists(){

        String tempDirectoryStr = System.getProperty( "java.io.tmpdir" );

        File tempDirectory = new File( tempDirectoryStr );

        if ( ! tempDirectory.exists() ){

            throw new RuntimeException(
                "Could not find valid directory for system property, " +
                    tempDirectoryStr );
        }

        File svgDirectory =
            new File( tempDirectory, "SVG_TEMP_DIRECTORY_TEST" );


        if ( svgDirectory.exists() && svgDirectory.isDirectory() ){

            deleteRecursively( svgDirectory );
        }

        return svgDirectory;
    }


    @Before
    public void beforeTest(){

        logger.debug( "@Before: Starting generating SVG files" );

        try {
            File svgDirectoryToGenerateSvgFilesTo =
                deleteSvgTempDirectoryIfExists();

            if ( ! svgDirectoryToGenerateSvgFilesTo.mkdir() ){

                fail( "Could not make svgDirectory, " +
                        svgDirectoryToGenerateSvgFilesTo.getAbsolutePath() );
            }

            svgFiles = start.start( svgDirectoryToGenerateSvgFilesTo );

            assertNotNull( "SvgFiles should not be null", svgFiles );
            svgDataRetriever = start.getSvgDataRetriever();
        }
        catch( Exception e ){

            logger.error( "Unexpected exception creating svg files", e );
            fail( "Unexpected exception creating svg files" );
        }

        logger.debug( "@Before: Finished generating SVG files" );
    }


    @After
    public void afterAllTests(){

        logger.debug( "@After: Starting Clean up of test" );

        // deleteSvgTempDirectoryIfExists();

        logger.debug( "@After: Finished Clean up of test" );
    }


    @Test
    public void test() throws SvgDataRetrieverException{

        // testExpectSameNumberOfSvgFileAsNumberOfAdvertSections();
        testFilesInSvgFileExist();
    }


    public void testExpectSameNumberOfSvgFileAsNumberOfAdvertSections()
        throws SvgDataRetrieverException{

        int actualNumberFilesGenerated = svgFiles.size();

        SortedSet<AdvertSection> advertSections =
            svgDataRetriever.getCachedAdvertSections();

        int expectedNumberFiles = advertSections.size();

        assertEquals(
            "Expect same number of SvgFile as number of AdvertSection",
                expectedNumberFiles, actualNumberFilesGenerated );
    }


    public void testFilesInSvgFileExist() {

        for ( File svgFile : svgFiles ){

            if ( ! svgFile.exists() ){

                fail( "SvgFile, " + svgFile.getAbsolutePath() +
                        " does not exist" );
            }
        }
    }


    public static void main( String args[] ){

        // Result result = org.junit.runner.JUnitCore.runClasses( MyTest.class );
        // int failureCount = result.getFailureCount();
        // System.out.println( "failurecount = " + failureCount );

        JUnitCore junitCore = new JUnitCore();
        junitCore.addListener( new TextListener( System.out ) );
        junitCore.run( StartTest.class );
    }
    **/
}
