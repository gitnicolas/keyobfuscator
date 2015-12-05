package tools.obfuscation.keyobfuscator.exception;

public class InvalidMaskException extends Exception {
	private final String message;

	public InvalidMaskException(String message) {
		this.message = message;
	}

	public InvalidMaskException(String message, Exception subException) {
		this(message);
		this.setStackTrace(subException.getStackTrace());
	}

	public String getMessage() {
		return message;
	}
}
