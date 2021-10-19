package main.event;

import java.util.Arrays;

import main.Timable;
import main.Timer;

public class TimulateEvent <T, R> implements ITestEvent<T, R>{
	private Timable<T, R> timable;
	private Timer timer;
	
	private T arg;
	private R result;
	
	private int pool_index;
	private int pool_size;
	
	private String stacktrace, shorttrace;
	
	private static String form_shorttrace = "[%s]\t%s\t( ~%fms)";
	private static String form_stacktrace = 
			"Test (%d/%d)\n" +
			"%s: \n" +
			"\tInput : %s\n" +
			"\tResult: %s\n" +
			"\tLap   : ~ %fs";
	
	
	public TimulateEvent(Timable<T, R> timable, T arg, R result, Timer timer, int pool_index, int pool_size) {
		this.timable 	= timable;
		this.timer		= timer;
		this.arg		= arg;
		this.result     = result;
		this.pool_index = pool_index;
		this.pool_size  = pool_size;
		
		formatTraces();
	}
	
	@Override
	public T getArgument() {
		return arg;
	}

	@Override
	public R getResult() {
		return result;
	}
	
	public Timable<T, R> getTimable() {
		return timable;
	}

	public Timer getTimer() {
		return timer;
	}
	
	private void formatTraces() {
		String arg_str = arg.getClass().isArray()    ? Arrays.toString((int[])arg)    : arg.toString();
		String res_str = result.getClass().isArray() ? Arrays.toString((int[])result) : result.toString();
		String name	   = timable.getName();

		double lap_s   = timer.lapAverage()/1000D;
		double lap	   = timer.lapAverage();
		
		shorttrace = String.format(form_shorttrace, name, res_str, lap);
		stacktrace = String.format(form_stacktrace, pool_index, pool_size, name, arg_str, res_str, lap_s);
		
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
