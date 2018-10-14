import java.util.Arrays;
public class Test {
	
	public static void main(String[] args) {
		String s = "abccabc";
		
		System.out.println(Arrays.toString(s.split("c")));
	}
}