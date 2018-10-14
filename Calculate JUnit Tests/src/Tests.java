import static org.junit.Assert.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class Tests {
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void squareTest(final int x, final int expected) {
		assertEquals(expected, Calculate.square(x));
	}
	
	public static Object[] parametersForSquareTest() {
		return new Object[] {
				new Object[] {1, 1},
				new Object[] {-4, 16},
				new Object[] {0, 0},
				new Object[] {100, 10000},
				new Object[] {-1, 1}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void cubeTest(final int x, final int expected) {
		assertEquals(expected, Calculate.cube(x));
	}
	
	public static Object[] parametersForCubeTest() {
		return new Object[] {
				new Object[] {1, 1},
				new Object[] {-4, -64},
				new Object[] {0, 0},
				new Object[] {100, 1000000},
				new Object[] {-1, -1}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void average2Test(final double a, final double b, final double expected) {
		assertEquals(expected, Calculate.average(a, b), 0);
	}
	
	public static Object[] parametersForAverage2Test() {
		return new Object[] {
				new Object[] {1, 1, 1},
				new Object[] {5, 10, 7.5},
				new Object[] {-6, 16, 5},
				new Object[] {0, 0, 0},
				new Object[] {-1, 1, 0}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void average3Test(final double a, final double b, final double c, final double expected) {
		assertEquals(expected, Calculate.average(a, b, c), 0);
	}
	
	public static Object[] parametersForAverage3Test() {
		return new Object[] {
				new Object[] {1, 1, 1, 1},
				new Object[] {5, 10, 7, 22/3.0},
				new Object[] {-6, 16, -10, 0},
				new Object[] {0, 0, 0, 0},
				new Object[] {-1, 1, 0, 0}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void toDegreesTest(final double x, final double expected) {
		assertEquals(expected, Calculate.toDegrees(x), 0);
	}
	
	public static Object[] parametersForToDegreesTest() {
		return new Object[] {
				new Object[] {3.14159, 180},
				new Object[] {6.28318, 360},
				new Object[] {0, 0},
				new Object[] {-1.570795, -90},
				new Object[] {-1, -57.295827908797776}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void toRadiansTest(final double x, final double expected) {
		assertEquals(expected, Calculate.toRadians(x), 0);
	}
	
	public static Object[] parametersForToRadiansTest() {
		return new Object[] {
				new Object[] {180, 3.14159},
				new Object[] {360, 6.28318},
				new Object[] {0, 0},
				new Object[] {-90, -1.570795},
				new Object[] {-57.295827908797776, -1}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void discriminantTest(final double a, final double b, final double c, final double expected) {
		assertEquals(expected, Calculate.discriminant(a,  b,  c), 0);
	}
	
	public static Object[] parametersForDiscriminantTest() {
		return new Object[] {
				new Object[] {0, 0, 0, 0}
//				new Object[] {360, 6.28318},
//				new Object[] {0, 0},
//				new Object[] {-90, -1.570795},
//				new Object[] {-57.295827908797776, -1}
		};
	}
	
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void minTest(final double a, final double b, final double expected) {
		assertEquals(expected, Calculate.min(a,  b), 0);
	}
	
	public static Object[] parametersForMinTest() {
		return new Object[] {
				new Object[] {1, 2, 1},
				new Object[] {-1, 2, -1},
				new Object[] {0.25, -0.61, -0.61},
				new Object[] {631, 350, 350},
				new Object[] {-57.29, -1, -57.29}
		};
	}


//	@Test
//	@Parameters
//	@TestCaseName("{method}({params})")
//	public void gcfTest(final int a, final int b, final int expected) {
//		assertEquals(expected, Calculate.gcf(a, b));
//	}
//	
//	public static Object[] parametersForGcfTest() {
//		return new Object[] {
//				new Object[] {2, 4, 2},
//				new Object[] {6, 14, 2},
//				new Object[] {5, 21, 1},
//				new Object[] {318, 531, 3},
//				new Object[] {500, 150, 50}
//		};
//	}
	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void sqrtTest(double x, double expected) {
		assertEquals(expected, Calculate.sqrt(x), 0);
	}
	
	public static Object[] parametersForSqrtTest() {
		return new Object[] {
				new Object[] {0, 0},
				new Object[] {4, 2},
				new Object[] {2, 1.41},
				new Object[] {10, 3.16},
				new Object[] {1, 1}
		};
	}

	
	@Test
	@Parameters
	@TestCaseName("{method}({params})")
	public void quadFormTest(final int a, final int b, final int c, final String expected) {
		assertEquals(expected, Calculate.quadForm(a, b, c));
	}
	
	public static Object[] parametersForQuadFormTest() {
		return new Object[] {
				new Object[] {0, 0, 0, "0.0"},
				new Object[] {1, 0, 0, "0.0"},
				new Object[] {1, 0, 1, "no real roots"},
				new Object[] {12, -53, -4, "-0.07 and 4.49"},
				new Object[] {-2, 19, 7, "-0.36 and 9.86"},
				new Object[] {1, -6, 9, "3.0"},
				new Object[] {1, -12, -7, "-0.56 and 12.56"},
				new Object[] {-15328, 175, 13785, "-0.94 and 0.95"}
		};
	}
}
