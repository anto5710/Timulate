package main.timulator;

import java.util.Map;
import java.util.Set;

import main.Timer;
import main.event.TimulateEvent;
import main.test.Timable;

/**
 * A Timulator is defined as a Particulatizing encompasser that maps a Timable solver to individual timers.
 * Its purpose is to timulate, an act of mass-testing a test-set for each Timable solvers to compare their efficiencies.
 * 
 * @author anto5710
 *
 * @param <F> A timable task solver or responder.
 * @param <T> The argument-type of tests.
 * @param <R> The return-type of tests.
 */
public interface ITimulator<F extends Timable<T, R>, T, R> {
	
	public Timer get(F timable);
	public Map<F, Timer> getTimerMap();
	public Set<F> getTimables();

	@SuppressWarnings("unchecked")
	public boolean add(F ... timables);
	public Timer remove(F timable);
	public void removeAll();
	
	public void timulate(int size);

	public int  getTestsetSize();
	public void setTestsetSize(int size);
		
	public void succeed(TimulateEvent<T, R> e);
	public void fail(TimulateEvent<T, R> e);
}
