package main;

public interface Timable <T, R> {
	public String getName();
	public R timulate(T arg);
}
