package main.logger;

import java.util.function.Function;

public class PLogger extends Logger{
	protected String delimiter;
	protected String bracket_open, bracket_close;
	
	public void printString(Object str) {
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
			printString(obj);
		}
	}
	
	public void printMatrix(Object[] matrix) {
		printMatrix(matrix, "", "", "\t", true);
	}
	
	protected void printMatrix(Object[] matrix, String bracket_open, String bracket_close, String delimiter, boolean outermost) {
		if(matrix == null) {
			print(matrix);
			return;
		}
		
		printString(bracket_open);
		indentTo(bracket_open);
		
		for(int i = 0; i < matrix.length; i++) {

			Object e = matrix[i];
			if(isMatrix(e)) {
				printMatrix((Object[]) e, bracket_open, bracket_close, delimiter, false);
				
			} else {
				print(e);
			}
			
			if(i < matrix.length - 1) {
				printString(delimiter);
				
				if (outermost) {
					endl();
				}
			}
		}
		
		if (!bracket_open.isEmpty()) {
			dedent();
		}

		printString(bracket_close);
	}
	
	private boolean isMatrix(Object array) {
		return array != null && array instanceof Object[];
	}
	
	private void iterateArray(Object array, int length, Function<Integer, String> iterator) {
		iterateArray(array, length, "[", "]", ", ", iterator);
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
