package main.logger;

import java.util.function.Function;

public class PLogger extends Logger{
	protected String delimiter;
	protected String bracket_open, bracket_close;
	
	public PLogger(String delimiter, String bracket_open, String bracket_close) {
		super();
		this.delimiter = delimiter;
		this.bracket_open = bracket_open;
		this.bracket_close = bracket_close;
	}
	
	public PLogger() {
		this(", ", "[", "]");
	}
	
	protected void printPlain(String str) {
		super.print(str);
	}
	
	public void print(Object obj) {
		if(obj != null && obj.getClass().isArray()) {
			if(obj instanceof Object[]) {
				printArray((Object[])obj);
				
			} else if(obj instanceof int[]) {
				printArray((int[])obj);
				
			} else if(obj instanceof short[]) {
				printArray((short[])obj);
				
			} else if(obj instanceof byte[]) {
				printArray((byte[])obj);
				
			} else if(obj instanceof long[]) {
				printArray((long[])obj);
				
			} else if(obj instanceof double[]) {
				printArray((double[])obj);
				
			} else if(obj instanceof float[]) {
				printArray((float[])obj);
				
			} else if(obj instanceof boolean[]) {
				printArray((boolean[])obj);
				
			} else if(obj instanceof char[]) {
				printArray((char[])obj);
			}
			
		} else {
			super.print(obj);
		}
	}
	
	public void printArray(Object[] matrix) {
		if(matrix == null) {
			print(matrix);
			return;
		}
		
		
		printPlain(bracket_open);
		for(int i = 0; i < matrix.length; i++) {
			print(matrix[i]);
			
			if(i < matrix.length - 1) {
				printPlain(delimiter);
			}
		}
		printPlain(bracket_close);
	}
	
	private void iterateArray(Object array, int length, Function<Integer, String> iterator) {
		iterateArray(array, length, getBracketOpen(), getBracketClose(), getDelimiter(), iterator);
	}
	
	private void iterateArray(Object array, int length, String bracket_open, String bracket_close, String delimiter, 
							  Function<Integer, String> iterator) {
		if(array == null) {
			print(array);
			return;
		}
		
		String array_str = bracket_open;

		for(int i = 0; i < length; i++) {
			array_str += iterator.apply(i);
			
			if(i < length - 1) {
				array_str += delimiter;
			}
		}
		
		array_str += bracket_close;
		print(array_str);
	}
	
	public void println(Object msg) {
		if(getIndentLevel() == 0) {
			printHeader();
		}
		print(msg.toString().concat(getLineBreak()));
	}
	
	public void printArray(double [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(float [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(char [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(int [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(boolean [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(long [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(short [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public void printArray(byte [] array) {
		iterateArray(array, array.length, 
		(i)-> {
			return String.valueOf(array[i]);
		});
	}
	
	public String getDelimiter() {
		return this.delimiter;
	}
	
	public String getBracketOpen() {
		return this.bracket_open;
	}
	
	public String getBracketClose() {
		return this.bracket_close;
	}
}
