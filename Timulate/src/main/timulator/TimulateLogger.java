package main.timulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.Timer;
import main.event.TimulateEvent;
import main.logger.GenericLogger;
import main.test.Test;
import main.test.Timable;

public class TimulateLogger<F extends Timable<T, R>, T, R> extends GenericLogger {
	private ITimulator<F, T, R> timulator;
	
	public TimulateLogger(ITimulator<F, T, R> timulator, String master_header) {
		super(master_header);
		this.timulator = timulator;
	}
	
	public TimulateLogger(ITimulator<F, T, R> timulator) {
		this(timulator, "[TEST-LOG]: ");
	}

	public ITimulator<F, T, R> getTimulator() {
		return timulator;
	}
	
	public void headTestset() {}
	
	private Comparator<F> ascend_sorter = new Comparator<F>() {

		@Override
		public int compare(F o1, F o2) {
			return (int) (1000 * (timulator.get(o1).lapAverage() - timulator.get(o2).lapAverage()));
		}
	};
	
	public void footTestset() {
		print(bar(132, 1.0, "-", ""));
		endl();
		
		List<F> timables = new ArrayList<F>(timulator.getTimables());
		Collections.sort(timables, ascend_sorter);
		
		printMaster();
		print("[RESULTS]: ");
		endl();
		
		for (int i = 0; i < timables.size(); i++) {
			F timable = timables.get(i);
			
			Timer timer = timulator.get(timable);
			double avgs = timer.lapAvarageSeconds();
			
			String indexer = "#" + (i+1) + ":\t";
				
			printf("[%s] -- Average: %fs", indexer, timable.getName(), avgs);
		
			if (i == 0) {
				print(" [FASTEST]");
			} else if(i == timables.size() - 1) {
				print(" [SLOWEST]");
			}
			
			dedent();
			endl();
		}
	}
	
	public void succeed(TimulateEvent<T, R> e) {}
	
	public void fail(TimulateEvent<T, R> e) {
		setStreamError();
		
		String name = "[" + e.getTimable().getName() + "]: ";
		printfln("TEST FAIL", name);
		printStackTrace(e);
		
		dedent();
//		dedentAll();
		
		setStreamOut();
	}
	
	public void headTest(Test<T, R> test, int index, int size) {
		double p = 1D * (index+1)/size;
		
		printMaster();
		printf("#%d (%.1f%%) %s", null, index+1, 100D*p, bar(105, p, "=", " "));
		endl();
		endl();
	}
	
	public void printShortTrace(TimulateEvent<T, R> e) {
		R response = e.getResponse();

		String name	 = "[" + e.getTimable().getName() + "]: ";
		double lap	 = e.getTimer().lapAverage();	
		
		print(response, name);
		printf("\t( ~ %fms)", null, lap);
	
		dedent();
		endl();
	}
	
	public void printStackTrace(TimulateEvent<T, R> e) {
		T arg	   = e.getArgument();
		R answer   = e.getTest().getAnswer();
		R response = e.getResponse();
		
		String name	 = e.getTimable().getName();
//		double lap	 = e.getTimer().lapAverage();
		long last_lap = e.getTimer().lastLap();
		
		printf("Test (%d/%d)", "", e.getIndex(), e.getSize());
		endl();
		
		print("", name);
		endl();
		
			print(arg, 		"Argument: ");
			dedentln();
			
			print(response, "Response: ");
			dedentln();
			
			print(answer, 	"Answer  : ");
			dedentln();
			
			printf(" ~ %dms", "Last Lap: ", last_lap);
			dedentln();
	
		
		dedentAll();
		endl();
	}
	
	public void footTest(Test<T, R> test, int index, int size) {
		dedentAllln();
		endl();
	}
	
	public void headResponse(F timable, Test<T, R>test, int index, int size) {}
	
	public void footResponse(TimulateEvent<T, R> e) {
		printShortTrace(e);
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
