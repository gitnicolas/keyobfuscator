package tools.obfuscation.keyobfuscator.exception;

public class InvalidInputException extends Exception {
	private final String subType;

	public InvalidInputException(String subType) {
		this.subType = subType;
	}

	public String getMessage() {
		return super.getMessage() + ", " + subType;
	}
}
