import static org.junit.Assert.*;

import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class Tests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	private final double ERROR = 0.1D;

	@Before
	public void setUpStreams() {
		// Set up streams to track output
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		// Restore to default streams
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	
	@Test
	@Parameters
	public void quadraticTest(final double a, final double b, final double c, final HashMap<String, Object> expected) {
		
		// Run method
		Quadratic.quadrDescriber(a, b, c);
		
		String result = outContent.toString();
		
		// Replace all \r\n with just \n; different systems output different things
		result = result.replaceAll("\\r\\n", "\n");
		
		// Opens
		
		int opensIndex = "Opens: ".length() + result.indexOf("Opens: ");
		if (opensIndex == -1) fail("Does not print whether quadratic opens up or down. Expected \"Opens: [value]\"");
		
		if (result.substring(opensIndex).indexOf("\n") == -1) {
			fail("Open direction: Use println instead of print");
		}
		
		int opensEndIndex = opensIndex + result.substring(opensIndex).indexOf("\n");
		String opensValue = result.substring(opensIndex, opensEndIndex);
		
		assertEquals("\n\tOpen direction incorrect:\n\t\t", expected.get("opens"), opensValue);
		
		// Axis of Symmetry
		
		int aosIndex = "Axis of Symmetry: ".length() + result.indexOf("Axis of Symmetry: ");
		if (aosIndex == -1) fail("Does not print axis of symmetry. Expected \"Axis of Symmetry: [value]\"");
		
		if (result.substring(aosIndex).indexOf("\n") == -1) {
			fail("Axis of symmetry: Use println instead of print");
		}
		
		int aosEndIndex = aosIndex + result.substring(aosIndex).indexOf("\n");
		String aosValueString = result.substring(aosIndex, aosEndIndex);
		
		double aosValue;
		try {
			aosValue = Double.parseDouble(aosValueString);
		} catch (Exception e) {
			fail("Printed axis of symmetry is not in valid decimal form.");
			return;
		}
		
		assertEquals("\n\tAxis of symmetry incorrect:\n\t\t", (double)expected.get("aos"), aosValue, ERROR);
		
		// Vertex
		
		int vertexIndex = "Vertex: ".length() + result.indexOf("Vertex: ");
		if (vertexIndex == -1) fail("Does not print vertex. Expected \"Vertex: [value]\"");
		
		if (result.substring(vertexIndex).indexOf("\n") == -1) {
			fail("vertex: Use println instead of print");
		}
		
		int vertexEndIndex = vertexIndex + result.substring(vertexIndex).indexOf("\n");
		String vertexValueString = result.substring(vertexIndex+1, vertexEndIndex-1);
		String[] vertexValueList = vertexValueString.split(", ");
		double vertexValueX, vertexValueY;
		try {
			vertexValueX = Double.parseDouble(vertexValueList[0]);
			vertexValueY = Double.parseDouble(vertexValueList[1]);
		} catch (Exception e) {
			fail("Printed vertex values are not in valid decimal form.");
			return;
		}
		
		assertEquals("\n\tx-coordinate of vertex incorrect:\n\t\t", (double) expected.get("vertexX"), vertexValueX, ERROR);
		assertEquals("\n\ty-coordinate of vertex incorrect:\n\t\t", (double) expected.get("vertexY"), vertexValueY, ERROR);
		
		// X intercept(s)
		
		int xInterceptIndex = "x-intercept(s): ".length() + result.indexOf("x-intercept(s): ");
		if (xInterceptIndex == -1) fail("Does not print x-intercept(s). Expected \"x-intercept(s): [value]\"");
		
		if (result.substring(xInterceptIndex).indexOf("\n") == -1) {
			fail("x-intercepts: Use println instead of print");
		}
		
		int xInterceptEndIndex = xInterceptIndex + result.substring(xInterceptIndex).indexOf("\n");
		String xInterceptValueString = result.substring(xInterceptIndex, xInterceptEndIndex);
		
		Object expectedXIntObj = expected.get("xIntercept");
		
		if (expectedXIntObj.getClass().equals(String.class)) {
			// No roots
			String expectedXInt = (String)expected.get("xIntercept");
			assertEquals("\n\tx-intercept incorrect:\n\t\t", expectedXInt, xInterceptValueString);
		} else {
			double[] expectedXIntList = (double[]) expected.get("xIntercept");
			
			if (expectedXIntList.length == 1) {
				// One root
				if (xInterceptValueString.contains(" and ")) {
					fail("quadrDescription incorrectly identified the number of roots. Expected 1 root, got 2 roots");
					return;
				}

				double expRoot = expectedXIntList[0];
				
				double root;
				try {
					root = Double.parseDouble(xInterceptValueString);
				} catch (Exception e) {
					fail("Printed x-intercept is not in valid decimal form.");
					return;
				}
				
				assertEquals(expRoot, root, ERROR);
			} else { // if length == 2
				// Two roots
				double expRoot1 = expectedXIntList[0];
				double expRoot2 = expectedXIntList[1];
				
				double root1, root2;
				int andIndex = xInterceptValueString.indexOf(" and ");
				
				if (andIndex == -1) {
					fail("quadrDescription incorrectly identified the number of roots. Expected 2 roots, got 1 root");
					return;
				}
				
				try {
					root1 = Double.parseDouble(xInterceptValueString.substring(0, andIndex));
					root2 = Double.parseDouble(xInterceptValueString.substring(andIndex + " and ".length()));
				} catch (Exception e) {
					fail("Printed x-intercepts are not in valid decimal form.");
					return;
				}
				
				assertEquals("\n\tFirst x-intercept incorrect:\n\t\t", expRoot1, root1, ERROR);
				assertEquals("\n\tSecond x-intercept incorrect:\n\t\t", expRoot2, root2, ERROR);
				
			}
		}
		
		// Y Intercept
		
		int yInterceptIndex = "y-intercept: ".length() + result.indexOf("y-intercept: ");
		if (yInterceptIndex == -1) fail("Does not print y-intercept. Expected \"y-intercept: [value]\"");
		
		if (result.substring(yInterceptIndex).indexOf("\n") == -1) {
			fail("y-intercept: Use println instead of print");
		}
		
		int yInterceptEndIndex = yInterceptIndex + result.substring(yInterceptIndex).indexOf("\n");
		String yInterceptValueString = result.substring(yInterceptIndex, yInterceptEndIndex);
		
		double yInterceptValue;
		try {
			yInterceptValue = Double.parseDouble(yInterceptValueString);
		} catch (Exception e) {
			fail("Printed y-intercept is not in valid decimal form.");
			return;
		}
		
		assertEquals("\n\ty-intercept incorrect:\n\t\t",
				(double) expected.get("yIntercept"), yInterceptValue, ERROR);
	}
	
	public static Object[] parametersForQuadraticTest() {
		/*
		 * Template:
		 * 
		 * new Object[] {a, b, c, new HashMap<String, Object>() {
		 * 		private static final long serialVersionUID = 1L;
		 * {
		 * 		put("opens", 		[String]);
		 * 		put("aos", 			[double]);
		 * 		put("vertexX", 		[double]);
		 * 		put("vertexY", 		[double]);
		 * 		put("xIntercept", 	[String] or [double[]]);
		 * 		put("yIntercept", 	[double]);
		 * }}}
		 */
		
		return new Object[] {
				// Quadrant 1
				
				// Quadrant 2
				
				new Object[] {1, 2, 3, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Up");
					put("aos", 			-1.0D);
					put("vertexX", 		-1.0D);
					put("vertexY", 		2.0D);
					put("xIntercept", 	"None");
					put("yIntercept", 	3.0);
				}}},
				
				new Object[] {2, 0, 2, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Up");
					put("aos", 			0.0D);
					put("vertexX", 		0.0D);
					put("vertexY", 		2.0D);
					put("xIntercept", 	"None");
					put("yIntercept", 	2.0D);
				}}},
				
				new Object[] {-4, -4, 4, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			-0.5D);
					put("vertexX", 		-0.5D);
					put("vertexY", 		5.0D);
					put("xIntercept", 	new double[] {-1.61D, 0.62D});
					put("yIntercept", 	4.0D);
				}}},
				
				new Object[] {-0.1, -5, 5, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			-25.0D);
					put("vertexX", 		-25.0D);
					put("vertexY", 		67.5D);
					put("xIntercept", 	new double[] {-50.99D, 1.0D});
					put("yIntercept", 	5.0D);
				}}},

				new Object[] {-1, -2, 1, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			-1.0D);
					put("vertexX", 		-1.0D);
					put("vertexY", 		2.0D);
					put("xIntercept", 	new double[] {-2.41D, 0.42D});
					put("yIntercept", 	1.0D);
				}}},
				
				// Quadrant 3
				
				// Quadrant 4

				new Object[] {3, -9, 2, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Up");
					put("aos", 			1.5D);
					put("vertexX", 		1.5D);
					put("vertexY", 		-4.75D);
					put("xIntercept", 	new double[] {0.24D, 2.76D});
					put("yIntercept", 	2.0D);
				}}},
				
				new Object[] {2, -4, -3, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Up");
					put("aos", 			1.0D);
					put("vertexX", 		1.0D);
					put("vertexY", 		-5.0D);
					put("xIntercept", 	new double[] {-0.58D, 2.58D});
					put("yIntercept", 	-3.0D);
				}}},
				
				new Object[] {-6, 7, -5, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			0.58D);
					put("vertexX", 		0.58D);
					put("vertexY", 		-2.95D);
					put("xIntercept", 	"None");
					put("yIntercept", 	-5.0D);
				}}},
				
				new Object[] {-2, 4, -99, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			1.0D);
					put("vertexX", 		1.0D);
					put("vertexY", 		-97.0D);
					put("xIntercept", 	"None");
					put("yIntercept", 	-99.0D);
				}}},
				
				
				// Y axis
				
				// X axis

				new Object[] {1, 6, 9, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Up");
					put("aos", 			-3.0D);
					put("vertexX", 		-3.0D);
					put("vertexY", 		0.0D);
					put("xIntercept", 	new double[] {-3.0D});
					put("yIntercept", 	9.0D);
				}}},
				

				new Object[] {3, 18, 27, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Up");
					put("aos", 			-3.0D);
					put("vertexX", 		-3.0D);
					put("vertexY", 		0.0D);
					put("xIntercept", 	new double[] {-3.0D});
					put("yIntercept", 	27.0D);
				}}},
				

				new Object[] {-1, -6, -9, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			-3.0D);
					put("vertexX", 		-3.0D);
					put("vertexY", 		0.0D);
					put("xIntercept", 	new double[] {-3.0D});
					put("yIntercept", 	-9.0D);
				}}},
				

				new Object[] {-2, -12, -18, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
				{
					put("opens", 		"Down");
					put("aos", 			-3.0D);
					put("vertexX", 		-3.0D);
					put("vertexY", 		0.0D);
					put("xIntercept", 	new double[] {-3.0D});
					put("yIntercept", 	-18.0D);
				}}},
		};
	}

}
