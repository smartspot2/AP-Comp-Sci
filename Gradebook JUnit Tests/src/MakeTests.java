import java.util.Random;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
//import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class MakeTests {
	
	private static final String[] NAMES = {"Edwina", 
			"Autumn", 
			"Nakisha", 
			"Nelson", 
			"Kristy", 
			"Janet", 
			"Kandra", 
			"Twanna", 
			"Roderick", 
			"Lacy", 
			"Ocie", 
			"Rosetta", 
			"Tenesha", 
			"Shanon", 
			"Fredericka", 
			"Corazon", 
			"Maryetta", 
			"Caroyln", 
			"Brittanie", 
			"Nicki", 
			"Lyle", 
			"Becky", 
			"Sharolyn", 
			"Grady", 
			"Leandro", 
			"Debora", 
			"Tanja", 
			"Neida", 
			"Cami", 
			"Dahlia", 
			"Jenee", 
			"Chae", 
			"Kacey", 
			"Granville", 
			"Sean", 
			"Gale", 
			"Jackson", 
			"Emely", 
			"Marcella", 
			"Arielle", 
			"Jenelle", 
			"Idell", 
			"Sharda", 
			"Erika", 
			"Man", 
			"Warren", 
			"Jayson", 
			"Yvonne", 
			"Rosanna", 
			"Leonora"};
	
	@SuppressWarnings("rawtypes")
	public static HashMap[] makeTests() throws FileNotFoundException, UnsupportedEncodingException {
		//PrintWriter writer = new PrintWriter("..\\gradebook\\gradebook-test.txt", "UTF-8");
		PrintWriter writer = new PrintWriter("gradebook-test.txt", "UTF-8");
		//PrintStream writer = System.out;
		
		Random rand = new Random();
		
		int numStudents = 1 + rand.nextInt(10);
		String[] studentNames = new String[numStudents];
		HashMap[] studentScores = new HashMap[numStudents];
		
		for (int stu = 0; stu < numStudents; stu++) {
			HashMap<String, Object> curScores = new HashMap<String, Object>();
			
			// Name
			studentNames[stu] = NAMES[rand.nextInt(NAMES.length)];
			writer.println(studentNames[stu]);
			
			// Homework
			int numHWs = 1 + rand.nextInt(20);
			
			writer.println("HW");
			writer.println(numHWs);
			
			double HWScore = 0;
			double HWPoints = 0;
			
			double[] HWPointsList = new double[numHWs];
			
			for (int i = 0; i < numHWs; i++) {
				// Total points
				double curHWPoints = 1 + rand.nextInt(10);
				HWPoints += curHWPoints;
				HWPointsList[i] = curHWPoints;
			}

			for (int i = 0; i < numHWs; i++) {
				// Scores & print
				double curHWScore = Double.parseDouble(String.format("%.2f", rand.nextInt((int)HWPointsList[i]) + rand.nextDouble()));
				HWScore += curHWScore;
				writer.println(curHWScore);
			}
			
			for (int i = 0; i < numHWs; i++) {
				// Print total points
				writer.println(HWPointsList[i]);
			}
			
			
			
			// Midterm
			
			double midtermPoints = 25 + rand.nextInt(76);
			double midtermScore = rand.nextInt((int)midtermPoints) + Math.round(rand.nextDouble() * 100) / 100.0;
			
			writer.println("Midterm");
			writer.println(midtermScore);
			writer.println(midtermPoints);
			
			// Final

			double finalPoints = 50 + rand.nextInt(51);
			double finalScore = rand.nextInt((int)finalPoints) + Math.round(rand.nextDouble() * 100) / 100.0;
			
			writer.println("Final");
			writer.println(finalScore);
			writer.println(finalPoints);
			
			// Overall calculations
			
			double overallScore = 0.5 * (HWScore / HWPoints * 100) + 0.2 * (midtermScore / midtermPoints * 100) + 0.3 * (finalScore / finalPoints * 100);
			String letterGrade;
			
			if (overallScore >= 90) {
				letterGrade = "A";
			} else if (overallScore >= 80) {
				letterGrade = "B";
			} else if (overallScore >= 70) {
				letterGrade = "C";
			} else if (overallScore >= 60) {
				letterGrade = "D";
			} else {
				letterGrade = "F";
			}
			
			// Put variables in HashMap
			curScores.put("studentName", studentNames[stu]);
			curScores.put("HWScore", HWScore / HWPoints * 100);
			curScores.put("midtermScore", midtermScore / midtermPoints * 100);
			curScores.put("finalScore", finalScore / finalPoints * 100);
			curScores.put("overallScore", overallScore);
			curScores.put("letterGrade", letterGrade);
			
			studentScores[stu] = curScores;
		}
		
		writer.println("end");
		
		writer.close();
		
		return studentScores;
	}
}
