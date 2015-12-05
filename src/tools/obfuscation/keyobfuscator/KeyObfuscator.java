package tools.obfuscation.keyobfuscator;

import tools.obfuscation.keyobfuscator.exception.InvalidInputException;
import tools.obfuscation.keyobfuscator.exception.InvalidMaskException;

public class KeyObfuscator {
	protected final String mask; // Format regular expression
	protected final long salt; // Encryption key
	protected final short[] bases; // List of digit bases (max 62)
	protected final String[] maps; // List of character maps (any combination of [0-9], [a-z] and [A-Z])
	protected final short length; // Number of digits of the input (quite limited, cause stored in a long)
	protected final long size; // Maximum numeric value of the input + 1
	protected final int weight; // Maximum binary length of the input (how many bits)

	protected static final boolean ENCRYPT = true;
	protected static final boolean DECRYPT = false;

	public KeyObfuscator(String mask, long salt) throws InvalidMaskException {
		this.mask = mask;
		this.salt = salt;

		checkMaskValidity();
		this.bases = computeBases();
		this.maps = computeMaps();

		this.length = (short) bases.length;
		long size = 1;
		for (int i = 0; i < length; i++) {
			size *= bases[i];
		}
		this.size = size;
		this.weight = (int) Math.floor(Math.log(size - 1) / Math.log(2)) + 1;
		if (weight > 63) throw new InvalidMaskException("TooLong");
	}

	protected void checkMaskValidity() throws InvalidMaskException {
		if (mask.isEmpty()) throw new InvalidMaskException("Empty");
		// TODO
	}

	protected short[] computeBases() {
		// TODO
		return new short[]{10, 26, 62};
	}

	protected String[] computeMaps() {
		// TODO
		String[] output = new String[3];
		output[0] = "0123456789";
		output[1] = "abcdefghijklmnopqrstuvwxyz";
		output[2] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return output;
	}

	public String encrypt(String input) throws InvalidInputException {
		checkInputValidity(input);
		long inputValue = stringToLong(input);
		long outputValue = crypt(inputValue, ENCRYPT);
		return longToString(outputValue);
	}

	public String decrypt(String input) throws InvalidInputException {
		checkInputValidity(input);
		long inputValue = stringToLong(input);
		long outputValue = crypt(inputValue, DECRYPT);
		return longToString(outputValue);
	}

	protected void checkInputValidity(String input) throws InvalidInputException {
		if (input.isEmpty()) throw new InvalidInputException("Empty");
		if (input.length() != length) throw new InvalidInputException("BadLength");
		// TODO
	}

	protected long stringToLong(String input) {
		long output = 0;

		for (int i = 0; i < length; i++) {
			output = output * bases[i] + maps[i].indexOf(input.charAt(i));
		}

		return output;
	}

	protected String longToString(long input) {
		String output = "";

		for (int i = length - 1; i >= 0; i--) {
			// TODO: Optimize string concatenation
			output = maps[i].charAt((int) input % bases[i]) + output;
			input /= bases[i];
		}

		return output;
	}

	protected long crypt(long input, boolean way) {
		long output = input;

		if (way) output = (output + salt) % size;

		int limit = weight / 2;
		for (int i = 0; i < limit; i++) {
			// IF i is even XOR the bit of the salt at this offset is set, THEN switch bits
			if (((i + 1) % 2) != (salt >> i & 1)) {
				if ((output >> i & 1) != (output >> (weight - 1 - i) & 1)) {
					output = output ^ (1 << i);
					output = output ^ (1 << (weight - 1 - i));
				}
			}
		}

		if (!way) output = output + size - (salt % size);

		return output;
	}
}
