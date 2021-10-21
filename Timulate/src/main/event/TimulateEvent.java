package main.event;

import main.Timer;
import main.test.Test;
import main.test.Timable;

public class TimulateEvent <T, R> implements ITestEvent<T, R>{
	private Timable<T, R> timable;
	private Timer timer;
	
	private Test<T, R> test;
	private R response;
	
	private int set_index;
	private int set_size;
	
	public TimulateEvent(Timable<T, R> timable, Test<T, R> test, R response, Timer timer, int set_index, int set_size) {
		this.timable	= timable;
		this.timer		= timer;
		this.test		= test;
		this.response	= response;
		this.set_index	= set_index;
		this.set_size	= set_size;
	}
	
	@Override
	public Test<T, R> getTest() {
		return test;
	}
	
	public T getArgument() {
		return test.getArgument();
	}

	@Override
	public R getResponse() {
		return response;
	}
	
	public Timable<T, R> getTimable() {
		return timable;
	}

	public Timer getTimer() {
		return timer;
	}
	
	public int getIndex() {
		return set_index;
	}
	
	public int getSize() {
		return set_size;
	}
}
