import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;


@RunWith(JUnitPlatform.class)
public class Tests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	private final double ERROR = 0.01D;
	
	private static int testNum = 1;
	
	@BeforeAll
	public static void resetTestNumber() {
		testNum = 1;
	}

	@BeforeEach
	public void setUpStreams() {
		// Set up streams to track output
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	public void restoreStreams() {
		// Restore to default streams
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	    testNum++;
	}
	
	@RepeatedTest(10)
	public void gradebookTest() throws FileNotFoundException, UnsupportedEncodingException {
		HashMap<String, Object>[] expected = new HashMap[] {};
		expected = MakeTests.makeTests();
		
		try {
			Gradebook.main(new String[] {});
		} catch (FileNotFoundException e) {
			fail("\n\tCannot find test file. Change the filename to \"gradebook-test.txt\"");
		}
		
		String result = outContent.toString();
		result = result.replaceAll("\\r\n", "\n");
		
		renameFile(testNum);
		
		String[] students = result.split("\n\n");
		assertEquals(expected.length, students.length, "\n\tNumber of printed students is incorrect. E");
		
		for (int index = 0; index < expected.length; index++) {
			String curResult = students[index];
			HashMap<String, Object> curExpected = expected[index];
			
			String[] lines = curResult.split("\n");
			
			// Name
			assertEquals(lines[0], curExpected.get("studentName"));
			
			// Overall percentage
			
			double expectedOverallPercentage = Double.parseDouble(String.format("%.2f", (double)curExpected.get("overallScore")));
			String[] overallLineSegments = lines[1].split("\\s+");	// Split whitespace
			
			assertEquals(overallLineSegments.length, 4, "\n\tInvalid output syntax; expected \"Overall = [Percentage], [Letter grade]\"");
			
			assertEquals(overallLineSegments[0], "Overall", "\n\tInvalid output syntax; expected \"Overall = [Percentage], [Letter grade]\"");
			assertEquals(overallLineSegments[1], "=", "\n\tInvalid output syntax; expected \"Overall = [Percentage], [Letter grade]\"");
			
			String overallPercentage = overallLineSegments[2];
			
			if (!overallPercentage.substring(overallPercentage.length() - 2).equals("%,")) {
				fail("\n\t" + overallPercentage + "\n\tEnd overall percentage with \"%\" and separate percentage and letter grade with \", \"");
			}
			
			overallPercentage = overallPercentage.substring(0, overallPercentage.length() - 2);	// because format = 123.45%,
			
			double overallPercentageValue = -1;
			try {
				overallPercentageValue = Double.parseDouble(overallPercentage);
			} catch (Exception e) {
				fail("\n\tInvalid overall percentage.");
			}
			
			assertEquals(expectedOverallPercentage, overallPercentageValue, "\n\tIncorrect overall percentage.");
			assertEquals(curExpected.get("letterGrade"), overallLineSegments[3], "\n\tIncorrect overall letter grade.");
			
			// Homework percentage
			
			double expectedHWPercentage = Double.parseDouble(String.format("%.2f", (double)curExpected.get("HWScore")));
			String[] HWLineSegments = lines[2].split("\\s+");	// Split whitespace
			
			assertEquals(HWLineSegments.length, 3, "\n\tInvalid output syntax; expected \"Homework = [Percentage]\"");
			
			assertEquals(HWLineSegments[0], "Homework", "\n\tInvalid output syntax; expected \"Homework = [Percentage]\"");
			assertEquals(HWLineSegments[1], "=", "\n\tInvalid output syntax; expected \"Homework = [Percentage]\"");
			
			String HWPercentage = HWLineSegments[2];
			
			if (!HWPercentage.substring(HWPercentage.length() - 1).equals("%")) {
				fail("\n\tEnd homework percentage with \"%\"");
			}
			
			HWPercentage = HWPercentage.substring(0, HWPercentage.length()-1);
			
			double HWPercentageValue = -1;
			try {
				HWPercentageValue = Double.parseDouble(HWPercentage);
			} catch (Exception e) {
				fail("\n\tInvalid homework percentage.");
			}
			
			assertEquals(expectedHWPercentage, HWPercentageValue, "\n\tIncorrect homework percentage.");
			
			// Midterm percentage
			
			double expectedMidtermPercentage = Double.parseDouble(String.format("%.2f", (double)curExpected.get("midtermScore")));
			String[] midtermLineSegments = lines[3].split("\\s+");	// Split whitespace
			
			assertEquals(midtermLineSegments.length, 3, "\n\tInvalid output syntax; expected \"Midterm = [Percentage]\"");
			
			assertEquals(midtermLineSegments[0], "Midterm", "\n\tInvalid output syntax; expected \"Midterm = [Percentage]\"");
			assertEquals(midtermLineSegments[1], "=", "\n\tInvalid output syntax; expected \"Midterm = [Percentage]\"");
			
			String midtermPercentage = midtermLineSegments[2];
			
			if (!midtermPercentage.substring(midtermPercentage.length() - 1).equals("%")) {
				fail("\n\tEnd midterm percentage with \"%\"");
			}
			
			midtermPercentage = midtermPercentage.substring(0, midtermPercentage.length()-1);
			
			double midtermPercentageValue = -1;
			try {
				midtermPercentageValue = Double.parseDouble(midtermPercentage);
			} catch (Exception e) {
				fail("\n\tInvalid midterm percentage.");
			}
			
			assertEquals(expectedMidtermPercentage, midtermPercentageValue, "\n\tIncorrect midterm percentage.");
			
			// Final percentage
			
			double expectedFinalPercentage = Double.parseDouble(String.format("%.2f", (double)curExpected.get("finalScore")));
			String[] finalLineSegments = lines[4].split("\\s+");	// Split whitespace
			
			assertEquals(finalLineSegments.length, 3, "\n\tInvalid output syntax; expected \"Final = [Percentage]\"");
			
			assertEquals(finalLineSegments[0], "Final", "\n\tInvalid output syntax; expected \"Final = [Percentage]\"");
			assertEquals(finalLineSegments[1], "=", "\n\tInvalid output syntax; expected \"Final = [Percentage]\"");
			
			String finalPercentage = finalLineSegments[2];
			
			if (!finalPercentage.substring(finalPercentage.length() - 1).equals("%")) {
				fail("\n\tEnd final percentage with \"%\"");
			}
			
			finalPercentage = finalPercentage.substring(0, finalPercentage.length()-1);
			
			double finalPercentageValue = -1;
			try {
				finalPercentageValue = Double.parseDouble(finalPercentage);
			} catch (Exception e) {
				fail("\n\tInvalid final percentage.");
			}
			
			assertEquals(expectedFinalPercentage, finalPercentageValue, "\n\tIncorrect final percentage.");
			
		}
	}
	
	private void renameFile(int n) {
		Path source = Paths.get(".\\gradebook-test.txt");
		File destFile = new File("gradebook-test" + n + ".txt");
		try {
			destFile.delete();
			Files.move(source, source.resolveSibling("gradebook-test" + n + ".txt"));
		} catch (Exception e) {
			// Just keep going 
		}
	}
}
