package tools.obfuscation.keyobfuscator.exception;

public class InvalidInputException extends Exception {
	private final String message;

	public InvalidInputException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
