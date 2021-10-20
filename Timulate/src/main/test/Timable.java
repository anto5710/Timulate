package main.test;

public interface Timable <T, R> {
	public String getName();
	public R respond(T arg);
}
