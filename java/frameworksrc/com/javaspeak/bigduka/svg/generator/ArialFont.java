package com.javaspeak.bigduka.svg.generator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ArialFont {

    public static Logger logger = Logger.getLogger( ArialFont.class );

    private Map<String,BigInteger> arialMap = new HashMap<String, BigInteger>();

    private int lineLengthInNumberUnits;

    public void setUp(){

        arialMap.put( "/", new BigInteger( "277" ) );
        arialMap.put( ".", new BigInteger( "277" ) );
        arialMap.put( "-", new BigInteger( "333" ) );
        arialMap.put( ",", new BigInteger( "277" ) );
        arialMap.put( "'", new BigInteger( "222" ) );
        arialMap.put( "+", new BigInteger( "584" ) );
        arialMap.put( "*", new BigInteger( "389" ) );
        arialMap.put( ")", new BigInteger( "333" ) );
        arialMap.put( "(", new BigInteger( "333" ) );
        arialMap.put( "&apos;", new BigInteger( "237" ) );
        arialMap.put( "&", new BigInteger( "722" ) );
        arialMap.put( "%", new BigInteger( "889" ) );
        arialMap.put( "$", new BigInteger( "556" ) );
        arialMap.put( "#", new BigInteger( "556" ) );
        arialMap.put( "\"", new BigInteger( "474" ) );
        arialMap.put( "!", new BigInteger( "333" ) );
        arialMap.put( " ", new BigInteger( "277" ) );
        arialMap.put( "?", new BigInteger( "610" ) );
        arialMap.put( ">;", new BigInteger( "584" ) );
        arialMap.put( "=", new BigInteger( "584" ) );
        arialMap.put( "<;", new BigInteger( "584" ) );
        arialMap.put( ";", new BigInteger( "333" ) );
        arialMap.put( ":", new BigInteger( "333" ) );
        arialMap.put( "9", new BigInteger( "556" ) );
        arialMap.put( "8", new BigInteger( "556" ) );
        arialMap.put( "7", new BigInteger( "556" ) );
        arialMap.put( "6", new BigInteger( "556" ) );
        arialMap.put( "5", new BigInteger( "556" ) );
        arialMap.put( "4", new BigInteger( "556" ) );
        arialMap.put( "3", new BigInteger( "556" ) );
        arialMap.put( "2", new BigInteger( "556" ) );
        arialMap.put( "1", new BigInteger( "556" ) );
        arialMap.put( "0", new BigInteger( "556" ) );
        arialMap.put( "O", new BigInteger( "777" ) );
        arialMap.put( "N", new BigInteger( "722" ) );
        arialMap.put( "M", new BigInteger( "833" ) );
        arialMap.put( "L", new BigInteger( "610" ) );
        arialMap.put( "K", new BigInteger( "722" ) );
        arialMap.put( "J", new BigInteger( "556" ) );
        arialMap.put( "I", new BigInteger( "277" ) );
        arialMap.put( "H", new BigInteger( "722" ) );
        arialMap.put( "G", new BigInteger( "777" ) );
        arialMap.put( "F", new BigInteger( "610" ) );
        arialMap.put( "E", new BigInteger( "666" ) );
        arialMap.put( "D", new BigInteger( "722" ) );
        arialMap.put( "C", new BigInteger( "722" ) );
        arialMap.put( "B", new BigInteger( "722" ) );
        arialMap.put( "A", new BigInteger( "722" ) );
        arialMap.put( "@", new BigInteger( "975" ) );
        arialMap.put( "_", new BigInteger( "556" ) );
        arialMap.put( "^", new BigInteger( "584" ) );
        arialMap.put( "]", new BigInteger( "333" ) );
        arialMap.put( "\\", new BigInteger( "277" ) );
        arialMap.put( "[", new BigInteger( "333" ) );
        arialMap.put( "Z", new BigInteger( "610" ) );
        arialMap.put( "Y", new BigInteger( "666" ) );
        arialMap.put( "X", new BigInteger( "666" ) );
        arialMap.put( "W", new BigInteger( "943" ) );
        arialMap.put( "V", new BigInteger( "666" ) );
        arialMap.put( "U", new BigInteger( "722" ) );
        arialMap.put( "T", new BigInteger( "610" ) );
        arialMap.put( "S", new BigInteger( "666" ) );
        arialMap.put( "R", new BigInteger( "722" ) );
        arialMap.put( "Q", new BigInteger( "777" ) );
        arialMap.put( "P", new BigInteger( "666" ) );
        arialMap.put( "o", new BigInteger( "610" ) );
        arialMap.put( "n", new BigInteger( "610" ) );
        arialMap.put( "m", new BigInteger( "889" ) );
        arialMap.put( "l", new BigInteger( "277" ) );
        arialMap.put( "k", new BigInteger( "556" ) );
        arialMap.put( "j", new BigInteger( "277" ) );
        arialMap.put( "i", new BigInteger( "277" ) );
        arialMap.put( "h", new BigInteger( "610" ) );
        arialMap.put( "g", new BigInteger( "610" ) );
        arialMap.put( "f", new BigInteger( "333" ) );
        arialMap.put( "e", new BigInteger( "556" ) );
        arialMap.put( "d", new BigInteger( "610" ) );
        arialMap.put( "c", new BigInteger( "556" ) );
        arialMap.put( "b", new BigInteger( "610" ) );
        arialMap.put( "a", new BigInteger( "556" ) );
        arialMap.put( "`", new BigInteger( "333" ) );
        arialMap.put( "", new BigInteger( "749" ) );
        arialMap.put( "~", new BigInteger( "584" ) );
        arialMap.put( "}", new BigInteger( "389" ) );
        arialMap.put( "|", new BigInteger( "279" ) );
        arialMap.put( "{", new BigInteger( "389" ) );
        arialMap.put( "z", new BigInteger( "499" ) );
        arialMap.put( "y", new BigInteger( "556" ) );
        arialMap.put( "x", new BigInteger( "556" ) );
        arialMap.put( "w", new BigInteger( "777" ) );
        arialMap.put( "v", new BigInteger( "556" ) );
        arialMap.put( "u", new BigInteger( "610" ) );
        arialMap.put( "t", new BigInteger( "333" ) );
        arialMap.put( "s", new BigInteger( "556" ) );
        arialMap.put( "r", new BigInteger( "389" ) );
        arialMap.put( "q", new BigInteger( "610" ) );
        arialMap.put( "p", new BigInteger( "610" ) );

        /**
        arialMap.put( "Ã‚Â�", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å½", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Â�", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å’", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€¹", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å ", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€°", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Ë†", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€¡", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€ ", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€¦", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€ž", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Æ’", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€š", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Â�", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â‚¬", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å¸", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å¾", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Â�", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å“", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€º", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Å¡", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â„¢", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Ëœ", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€”", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€“", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€¢", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€�", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€œ", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€™", new BigInteger( "749" ) );
        arialMap.put( "Ã‚â€˜", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Â�", new BigInteger( "749" ) );
        arialMap.put( "Ã‚Â¯", new BigInteger( "552" ) );
        arialMap.put( "Ã‚Â®", new BigInteger( "736" ) );
        arialMap.put( "Ã‚Â­", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â¬", new BigInteger( "584" ) );
        arialMap.put( "Ã‚Â«", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Âª", new BigInteger( "370" ) );
        arialMap.put( "Ã‚Â©", new BigInteger( "736" ) );
        arialMap.put( "Ã‚Â¨", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â§", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Â¦", new BigInteger( "279" ) );
        arialMap.put( "Ã‚Â¥", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Â¤", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Â£", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Â¢", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Â¡", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â ", new BigInteger( "277" ) );
        arialMap.put( "Ã‚Â¿", new BigInteger( "610" ) );
        arialMap.put( "Ã‚Â¾", new BigInteger( "833" ) );
        arialMap.put( "Ã‚Â½", new BigInteger( "833" ) );
        arialMap.put( "Ã‚Â¼", new BigInteger( "833" ) );
        arialMap.put( "Ã‚Â»", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Âº", new BigInteger( "365" ) );
        arialMap.put( "Ã‚Â¹", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â¸", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â·", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â¶", new BigInteger( "556" ) );
        arialMap.put( "Ã‚Âµ", new BigInteger( "576" ) );
        arialMap.put( "Ã‚Â´", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â³", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â²", new BigInteger( "333" ) );
        arialMap.put( "Ã‚Â±", new BigInteger( "548" ) );
        arialMap.put( "Ã‚Â°", new BigInteger( "399" ) );
        arialMap.put( "ÃƒÂ�", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÅ½", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÂ�", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÅ’", new BigInteger( "277" ) );
        arialMap.put( "Ãƒâ€¹", new BigInteger( "666" ) );
        arialMap.put( "ÃƒÅ ", new BigInteger( "666" ) );
        arialMap.put( "Ãƒâ€°", new BigInteger( "666" ) );
        arialMap.put( "ÃƒË†", new BigInteger( "666" ) );
        arialMap.put( "Ãƒâ€¡", new BigInteger( "722" ) );
        arialMap.put( "Ãƒâ€ ", new BigInteger( "999" ) );
        arialMap.put( "Ãƒâ€¦", new BigInteger( "722" ) );
        arialMap.put( "Ãƒâ€ž", new BigInteger( "722" ) );
        arialMap.put( "ÃƒÆ’", new BigInteger( "722" ) );
        arialMap.put( "Ãƒâ€š", new BigInteger( "722" ) );
        arialMap.put( "ÃƒÂ�", new BigInteger( "722" ) );
        arialMap.put( "Ãƒâ‚¬", new BigInteger( "722" ) );
        arialMap.put( "ÃƒÅ¸", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÅ¾", new BigInteger( "666" ) );
        arialMap.put( "ÃƒÂ�", new BigInteger( "666" ) );
        arialMap.put( "ÃƒÅ“", new BigInteger( "722" ) );
        arialMap.put( "Ãƒâ€º", new BigInteger( "722" ) );
        arialMap.put( "ÃƒÅ¡", new BigInteger( "722" ) );
        arialMap.put( "Ãƒâ„¢", new BigInteger( "722" ) );
        arialMap.put( "ÃƒËœ", new BigInteger( "777" ) );
        arialMap.put( "Ãƒâ€”", new BigInteger( "584" ) );
        arialMap.put( "Ãƒâ€“", new BigInteger( "777" ) );
        arialMap.put( "Ãƒâ€¢", new BigInteger( "777" ) );
        arialMap.put( "Ãƒâ€�", new BigInteger( "777" ) );
        arialMap.put( "Ãƒâ€œ", new BigInteger( "777" ) );
        arialMap.put( "Ãƒâ€™", new BigInteger( "777" ) );
        arialMap.put( "Ãƒâ€˜", new BigInteger( "722" ) );
        arialMap.put( "ÃƒÂ�", new BigInteger( "722" ) );
        arialMap.put( "ÃƒÂ¯", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÂ®", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÂ­", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÂ¬", new BigInteger( "277" ) );
        arialMap.put( "ÃƒÂ«", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂª", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ©", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¨", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ§", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¦", new BigInteger( "889" ) );
        arialMap.put( "ÃƒÂ¥", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¤", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ£", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¢", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¡", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ ", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¿", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¾", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ½", new BigInteger( "556" ) );
        arialMap.put( "ÃƒÂ¼", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ»", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂº", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ¹", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ¸", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ·", new BigInteger( "548" ) );
        arialMap.put( "ÃƒÂ¶", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂµ", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ´", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ³", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ²", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ±", new BigInteger( "610" ) );
        arialMap.put( "ÃƒÂ°", new BigInteger( "610" ) );
        **/
    }

    private int getLengthWordInUnits( String word ){

        int length = 0;

        char[] chars = word.toCharArray();

        BigInteger charWidth;

        for ( char letter : chars ){
            
            charWidth = arialMap.get( new String( new char[]{letter}) );

            // as we do not have all characters in the map we assume the 
            // character to be very wide if it is not in the map
            if ( charWidth == null ){

                length = length + 777;
            }
            else {

                length = length + charWidth.intValue();
            }
        }

        return length;
    }


    public ArialFont( int maxNumberOfSameLetterOnLine, String letter ){

        setUp();

        BigInteger numberPerLine =
            new BigInteger( maxNumberOfSameLetterOnLine + "" );

        BigInteger widthLetter = arialMap.get( letter );

        lineLengthInNumberUnits =
            widthLetter.multiply( numberPerLine ).intValue();
    }


    public int getLineLengthInNumberUnits() {

        return lineLengthInNumberUnits;
    }


    public String[] getLines( String text ){

        StringTokenizer wordsTokenizer = new StringTokenizer( text.trim() );

        String word = "";
        int numberUnitsWideWord = 0;
        int numberUnitsWideCurrentLine = 0;
        int widthSpace = arialMap.get( " " ).intValue();

        List<StringBuffer> lines = new ArrayList<StringBuffer>();
        StringBuffer sb = new StringBuffer();
        lines.add( sb );

        while ( wordsTokenizer.hasMoreTokens() ){

            word = wordsTokenizer.nextToken();
            numberUnitsWideWord = getLengthWordInUnits( word );

            logger.debug( word + " : " + numberUnitsWideWord );

            if ( ( numberUnitsWideCurrentLine + numberUnitsWideWord ) <=
                        lineLengthInNumberUnits ){

                numberUnitsWideCurrentLine =
                    numberUnitsWideCurrentLine + numberUnitsWideWord +
                        widthSpace;
            }
            else {

                sb = new StringBuffer();
                lines.add( sb );

                numberUnitsWideCurrentLine = numberUnitsWideWord;
            }

            sb.append( word ).append( " " );
        }

        String[] linesToReturn = new String[ lines.size() ];

        int i = 0;

        for ( StringBuffer s : lines ){

            linesToReturn[ i ] = s.toString();
            i++;
        }

        return linesToReturn;
    }


    public static void main( String args[] ){

        String[] tests = new String[]{
            //"TTTTT TTTTT TTTTT TTTTT",
            //"iiiii iiiii iiiii iiiii",
            //"The Cat sat on the Mat and the dog chased it away",
            //"The Monster with many Eyes said iiii iiii iiii iiii"
            "Silk Dress for smart occasions."
        };

        ArialFont arialFont = new ArialFont( 25, "T" );

        logger.debug( "Max line size = " +
                arialFont.getLineLengthInNumberUnits() );

        for ( String test : tests ){

            String[] lines = arialFont.getLines( test );

            int count = 1;

            for ( String line : lines ){

                logger.debug( count + " : [" + line + "] size: " +
                        arialFont.getLengthWordInUnits( line ) );

                count++;
            }
        }
    }
 }
