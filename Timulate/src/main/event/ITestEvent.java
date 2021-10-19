package main.event;

public interface ITestEvent <T, R> {
	public T getArgument();
	public R getResult();
	
	public String getStackTrace();
	public String getShortTrace();
}
