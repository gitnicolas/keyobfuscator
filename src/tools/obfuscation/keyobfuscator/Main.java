package tools.obfuscation.keyobfuscator;

public class Main {

	public static void main(String[] args) {
		try {
			KeyObfuscator keyObfuscator;

			keyObfuscator = new KeyObfuscator("([0-9][a-z][0-9a-zA-Z])", 123456789);
			for (long i = 0; i < keyObfuscator.size; i++) {
				assert i == keyObfuscator.stringToLong(keyObfuscator.longToString(i));
			}

			for (long i = 0; i < keyObfuscator.size * 3; i++) {
				keyObfuscator = new KeyObfuscator("([0-9][a-z][0-9a-zA-Z])", i);
				for (long j = 0; j < keyObfuscator.size; j++) {
					assert j == keyObfuscator.crypt(keyObfuscator.crypt(j, KeyObfuscator.ENCRYPT), KeyObfuscator.DECRYPT);
				}
			}

			String input = "5cD";
			assert keyObfuscator.decrypt(keyObfuscator.encrypt(input)).equals(input);

			System.out.println("Ended successfully !");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Ended with error: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
