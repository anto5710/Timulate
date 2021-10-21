package main.timulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import main.event.TimulateEvent;
import main.test.Test;
import main.test.TestWriter;
import main.test.Timable;
import main.util.Timer;


public class Timulator <F extends Timable<T, R>, T, R> implements ITimulator<F, T, R>{
	protected Map<F, Timer> timerMap = new HashMap<>();
	
	private TestWriter<T, R> tester;
	private int set_size;
	public static final int DEFAULT_TESTSET_SIZE = 1000;
	
	private boolean fail_break = false;
	private TimulateLogger<F, T, R> logger;
	
	public Timulator(TestWriter<T, R> tester, int set_size, boolean fail_break) {
		setTestsetSize(set_size);
		
		this.tester = tester;
		
		this.logger = new TimulateLogger<F, T, R>(this);
		this.fail_break = fail_break;
	}
	
	public Timulator(TestWriter<T, R> tester, boolean fail_break) {
		this(tester, DEFAULT_TESTSET_SIZE, fail_break);
	}
	
	public Timulator(TestWriter<T, R> tester) {
		this(tester, DEFAULT_TESTSET_SIZE, false);
	}
	
	public void timulate() {
		timulate(set_size);
	}
	
	@Override
	public void timulate(int size) {
		if (timerMap.size() <= 0) {
			System.err.println("Empty timulator set!");
			return;
		}
		
		logger.headTestset();
		
		for (int i = 0; i < size; i++) {		
			Test<T, R> test = tester.generateTest();
		
			logger.headTest(test, i, size);
			
			boolean testrun = runTest(test, i, size);
			
			if(fail_break && !testrun) {
				break;
			}
			
			logger.footTest(test, i, size);
		}
		
		logger.footTestset();
	}

	protected boolean runTest(Test<T, R> test, int i, int size) {
		T arg = test.getArgument();
		
		for (F timable : getTimables()) {
			logger.headResponse(timable, test, i , size);
			
			Timer timer = get(timable);
			
			timer.startLap();
			R response = timable.respond(arg);
			timer.lap();
			
			TimulateEvent<T, R> e = new TimulateEvent<>(timable, test, response, timer, i, size);

			if (!test.isResolved()) {
				test.setAnswer(response);
			}
			
			if (tester.correct(test, response)) {
				logger.succeed(e);
				
			} else {
				logger.fail(e);
				if (fail_break) return false;
			}

			logger.footResponse(e);
		}
		
		return true;
	}
	
	
	
	@Override
	public Timer get(F timable) {
		return timable == null ? null : timerMap.get(timable);
	}

	@Override
	public Set<F> getTimables() {
		return timerMap.keySet();
	}

	@Override
	public Map<F, Timer> getTimerMap() {
		return timerMap;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean add(F ... timables) {
		if (timables == null) {
			return false;
		}
		
		for (F timable : timables) {
			timerMap.put(timable, new Timer());
		}
		
		return true;
	}

	@Override
	public Timer remove(F timable) {
		return timerMap.remove(timable);
	}

	@Override
	public void removeAll() {
		timerMap.clear();
	}
	
	@Override
	public int getTestsetSize() {
		return set_size;
	}

	@Override
	public void setTestsetSize(int size) {
		this.set_size = size >= 0 ? size : 1;  
	}

	public TestWriter<T, R> getTester() {
		return tester;
	}
	
	public boolean getFailBreak() {
		return fail_break;
	}
	
	public void setFailBreak(boolean fail_break) {
		this.fail_break = fail_break;
	}
	
	public static String bar(int width, double p, String fill, String empt) {
		int fill_l = (int) (width * p);
		
		String bar = "";
		for(int i = 0; i < fill_l; i+=fill.length()) {
			bar += fill;
		}
		
		for(int i = 0; i < width - fill_l; i+=empt.length()) {
			bar += empt;
		}
		return bar;
	}
}
