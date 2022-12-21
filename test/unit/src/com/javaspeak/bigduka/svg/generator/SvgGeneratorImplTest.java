package com.javaspeak.bigduka.svg.generator;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javaspeak.bigduka.svg.generator.AbstractSvgGenerator;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/applicationContext.xml" })
public class SvgGeneratorImplTest {

	// autowired ===============================================================
	@Autowired
	@Qualifier( "defaultSvgGenerator" )
	private AbstractSvgGenerator svgGenerator;
	// end autowired ===========================================================


	@Test
	public void testGetInteger(){


		String text =
			"<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"" +
			"x=\"#x#\" y=\"#y#\" width=\"128px\" height=\"70px\" " +
			"viewBox=\"0 0 128 70\">";

		/**
		String text = "<blah height=\"30\">";
		**/

		String attributeName = "height";
		int actual = svgGenerator.getInteger( attributeName, text );
		int expected = 70;

		assertEquals( expected, actual );
	}


	public static void main( String args[] ){

		// Result result =
		//      org.junit.runner.JUnitCore.runClasses( MyTest.class );
		// int failureCount = result.getFailureCount();
		// System.out.println( "failurecount = " + failureCount );

		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener( new TextListener( System.out ) );
		junitCore.run( SvgGeneratorImplTest.class );
	}
}
