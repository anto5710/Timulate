package main.util;

import java.util.Arrays;

public class Equator {
	
	public static boolean equalx(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return o1 == o2;
		}
		
		if (o1.getClass().isArray() && o1.getClass().equals(o2.getClass())) {
			return arrayEqualx(o1, o2);
			
		} else if (o1 instanceof Number && o2 instanceof Number) {
			return new Double(o1.toString()).equals(new Double(o2.toString()));
		}
		
		return o1.equals(o2);
	}
	
	private static boolean arrayEqualx(Object arr1, Object arr2) {
		if (arr1 instanceof Object[]) {
			return Arrays.deepEquals((Object[])arr1, (Object[])arr2);
		
		} else if(arr1 instanceof int[]) {
			return Arrays.equals((int[]) arr1, (int[]) arr2);
			
		} else if(arr1 instanceof long[]) {
			return Arrays.equals((long[]) arr1, (long[]) arr2);
			
		} else if(arr1 instanceof short[]) {
			return Arrays.equals((short[]) arr1, (short[]) arr2);
			
		} else if(arr1 instanceof byte[]) {
			return Arrays.equals((byte[]) arr1, (byte[]) arr2);
			
		} else if(arr1 instanceof boolean[]) {
			return Arrays.equals((boolean[]) arr1, (boolean[]) arr2);
			
		} else if(arr1 instanceof double[]) {
			return Arrays.equals((double[]) arr1, (double[]) arr2);
			
		} else if(arr1 instanceof float[]) {
			return Arrays.equals((float[]) arr1, (float[]) arr2);
			
		} else if(arr1 instanceof char[]) {
			return Arrays.equals((char[]) arr1, (char[]) arr2);
			
		}
		
		return arr1.equals(arr2);
	}
	
}
