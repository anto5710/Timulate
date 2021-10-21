package main.logger;

import java.util.function.Function;

public class GenericLogger extends Logger{
	protected ArrayFormat matrix_format = ArrayFormat.EMPTY_TABS;
	protected ArrayFormat array_format  = ArrayFormat.SQUARE_COMMA;
	
	public GenericLogger(String master_header) {
		super(master_header);
	}
	
	public GenericLogger() {
		super();
	}
	
	public void printToString(Object str) {
		super.print(str);
	}
	
	public void print(Object obj) {
		if(obj != null && obj.getClass().isArray()) {
			
			if(obj instanceof Object[]) {
				printMatrix((Object[])obj);
				
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
			printToString(obj);
		}
	}
	

	private void iterateArray(Object array, int length, Function<Integer, String> iterator) {
		iterateArray(array, length, array_format.bracketOpen(), 
									array_format.bracketClose(), 
									array_format.delimiter(), iterator);
	}
	
	public void printMatrix(Object[] matrix) {
		printMatrix(matrix, matrix_format.bracketOpen(), 
							matrix_format.bracketClose(), 
							matrix_format.delimiter(), 0);
	}
	
	protected void printMatrix(Object[] matrix, String bracket_open, String bracket_close, String delimiter, int depth) {
		if(matrix == null) {
			print(matrix);
			return;
		}
		
		printToString(bracket_open);
		indentTo(bracket_open);
		
		for(int i = 0; i < matrix.length; i++) {

			Object e = matrix[i];
			if(e != null && e instanceof Object[]) {
				printMatrix((Object[]) e, bracket_open, bracket_close, delimiter, depth - 1);
				
			} else {
				print(e);
			}
			
			if(i < matrix.length - 1) {
				printToString(delimiter);
				
				if (depth == 0) {
					endl();
				}
			}
		}
		
		if (!bracket_open.isEmpty()) {
			dedent();
		}

		printToString(bracket_close);
	}
	
	protected void iterateArray(Object array, int length, String bracket_open, String bracket_close, String delimiter, 
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
	
	public void printArray(double [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(float [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(char [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(int [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(boolean [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(long [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(short [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
	
	public void printArray(byte [] array) {
		iterateArray(array, array.length, (i) -> String.valueOf(array[i]));
	}
}
