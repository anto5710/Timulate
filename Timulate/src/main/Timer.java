package main;

/**
 * A System-time based timer, or more precisely, a lap-timer.<br>
 * Measures the interval between two points using startLap(), {action}, and lap().
 * 
 * @author anto5710
 *
 */
public class Timer {
	private long origin;
	private long lap_total;
	private int  lap_cnt;
	private long lap_start, last_lap;
	
	public Timer() {
		reset();
	}
	
	/**
	 * Resets all time-keeping records so far, and give a fresh new start.
	 */
	public void reset() {
		origin	  = -1;
		lap_total =  0;
		lap_cnt	  =  0;
		last_lap  =  0;
	}
	
	/**
	 * Starts a new lap. Any subseqeunt lap measure will be in the elapsed mills since this point.
	 */
	public void startLap() {
		lap_start = System.currentTimeMillis();
		
		if (origin == -1) {
			origin = lap_start;
		}
	}

	/**
	 * Calculates, in milliseconds, the elapsed time since the last startLap() call.
	 * @return The elapsed lap-time since the last lap start.
	 */
	public long lap() {
		last_lap = System.currentTimeMillis() - lap_start;
		lap_total += last_lap;
		lap_cnt ++;
		
		return last_lap;
	}
	
	/**
	 * Returns lastest lap measure, if any.<br>
	 * Set 0ms by default.
	 * @return The lastest lap duration in mills.
	 */
	public long lastLap() {
		return last_lap;
	}

	/**
	 * The total summation of all lap measures.<br>
	 * That is, excluding any extra time intervals between a lap and the next lap start.
	 * @return The accumulation of all lap measures.
	 */
	public long lapTotal() {
		return lap_total;
	}

	/**
	 * The physical time elapsed since the Timer instance started operating, which is precisely the first startLap() call.
	 * @return The absolute time since the first usage of this Timer instance.
	 */
	public long absTotal() {
		return System.currentTimeMillis() - origin;
	}
	
	/**
	 * The average of all lap measures in milliseconds.
	 * @return The average lap measure.
	 */
	public double lapAverage() {
		return 1D * lap_total / lap_cnt;
	}
	
	/**
	 * The average of all lap measures in seconds.
	 * @return The average lap measure.
	 */
	public double lapAvarageSeconds() {
		return millsToSeconds(lapAverage());
	}
	
	/**
	 * Converts a milliseconds time interval to seconds.
	 * @param mills A milliseconds time interval.
	 * @return The equivalent in seconds.
	 */
	public static double millsToSeconds(double mills) {
		return mills / 1000D;
	}
}
