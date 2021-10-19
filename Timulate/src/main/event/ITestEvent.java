package main.event;

import main.test.Test;

public interface ITestEvent <T, R> {
	public Test<T, R> getTest();
	public R getResponse();
	
	public String getStackTrace();
	public String getShortTrace();
}
