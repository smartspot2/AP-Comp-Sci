import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) {
		String s = "";
		System.out.println(s);
		
		String test = "Hello World";
		System.out.println(test);
		
		System.out.println(Arrays.toString(test.split("\\s")));
	}
}