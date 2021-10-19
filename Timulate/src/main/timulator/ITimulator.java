package main.timulator;

import java.util.Map;
import java.util.Set;

import main.Timable;
import main.Timer;

public interface ITimulator<F extends Timable<T, R>, T, R> {
	
	public Timer get(F timable);
	public Map<F, Timer> getTimers();
	public Set<F> getTimables();

	public boolean add(F timable);
	public boolean remove(F timable);
	
	public void timulate(int size);

	public void footerTest();
	public void headerTest(int i, int size);

	
	
//	public void footerLap(TimulateEvent<T, R> e);
//	public void fail(TimulateEvent<T, R> e);
}
