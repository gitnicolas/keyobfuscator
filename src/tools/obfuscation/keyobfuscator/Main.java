package tools.obfuscation.keyobfuscator;

public class Main {

	public static void main(String[] args) {
		try {
			KeyObfuscator keyObfuscator = new KeyObfuscator("([0-9][a-z][0-9a-zA-Z])", 123456789);
			String output = keyObfuscator.encrypt("5cD");
			System.out.println("Ended with result: " + output);
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Ended with error: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
