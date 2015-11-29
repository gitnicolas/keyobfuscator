package tools.obfuscation.keyobfuscator.exception;

public class InvalidMaskException extends Exception {
	private final String subType;

	public InvalidMaskException(String subType) {
		this.subType = subType;
	}

	public String getMessage() {
		return super.getMessage() + ", " + subType;
	}
}
