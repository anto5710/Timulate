package main.event;

import java.util.Arrays;

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
	
	private String stacktrace, shorttrace;
	
	private static String form_shorttrace = "[%s]\t%s\t( ~%fms)";
	private static String form_stacktrace = "Test (%d/%d)\n" +
											"%s: \n" +
											"\tArgument: %s\n" +
											"\tResponse: %s\n" +
											"\tAnswer  : %s\n" +
											"\tLap     : ~ %fs";
	
	public TimulateEvent(Timable<T, R> timable, Test<T, R> test, R response, Timer timer, int set_index, int set_size) {
		this.timable	= timable;
		this.timer		= timer;
		this.test		= test;
		this.response	= response;
		this.set_index	= set_index;
		this.set_size	= set_size;
		
		formatTraces();
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
	
	private void formatTraces() {	
		T arg = getArgument();
		R answer = getTest().getAnswer();
		
		String arg_str		= getString(arg);
		String response_str	= getString(response);
		String answer_str	= getString(answer);
		
		String name			= timable.getName();
		double lap_s		= timer.lapAverage()/1000D;
		double lap			= timer.lapAverage();
		
		shorttrace = String.format(form_shorttrace, name, response_str, lap);
		stacktrace = String.format(form_stacktrace, set_index, set_size, name, arg_str, response_str, answer_str, lap_s);
	}
	
	private String getString(Object obj) {
		if (obj == null) {
			return "<Undefined>";
		}
		
		if (obj instanceof int[]) {
			return Arrays.toString((int[])obj);
		}
		return obj.toString();
	}
	
	@Override
	public String getStackTrace() {
		return stacktrace;
	}

	
	@Override
	public String getShortTrace() {
		return shorttrace;
	}

}
