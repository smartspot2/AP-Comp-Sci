import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


import java.util.Scanner;

public class TestGradebook {

	public static void main(String args[]) {
		
		Result result = JUnitCore.runClasses(Tests.class);
		System.out.println(result.getRunCount() + " test(s) run.");
		System.out.println(result.getFailureCount() + " failure(s).");
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
		
		System.out.println("Press ENTER to quit...");
		Scanner s = new Scanner(System.in);
		s.nextLine();
		s.close();
		
	}

}
