import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.*;

public class CalculateTest {
	public static void main(String args[]) {
		Result result = JUnitCore.runClasses(Tests.class);
		System.out.println(result.getRunCount() + " test(s) run.");
		System.out.println(result.getFailureCount() + " failure(s).");
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
	}
}
