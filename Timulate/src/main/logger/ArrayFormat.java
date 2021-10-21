package main.logger;

public enum ArrayFormat {
	
	SQUARE_COMMA("[", "]", ","), EMPTY_TABS("", "", "\t"), CURLY_COMMA("{", "}", ", ");
	
	private String bracket_open;
	private String bracket_close;
	private String delimiter;
	
	private ArrayFormat(String bracket_open, String bracket_close, String delimiter) {
		this.bracket_close = bracket_close;
		this.bracket_open = bracket_open;
		this.delimiter = delimiter;
	}

	public String bracketClose() {
		return bracket_close;
	}

	public String bracketOpen() {
		return bracket_open;
	}

	public String delimiter() {
		return delimiter;
	}
}
