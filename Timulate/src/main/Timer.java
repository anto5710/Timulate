package main;

public class Timer {
	private long origin    = -1;
	private long lap_total =  0;
	private int  lap_cnt   =  0;
	private long lap_start, last_lap;
	
	public void startLap() {
		lap_start = System.currentTimeMillis();
		
		if (origin == -1) {
			origin = lap_start;
		}
	}

	public long lap() {
		last_lap = System.currentTimeMillis() - lap_start;
		lap_total += last_lap;
		lap_cnt ++;
		
		return last_lap;
	}
	
	public long lastLap() {
		return last_lap;
	}

	public long lapTotal() {
		return lap_total;
	}

	public long absTotal() {
		return System.currentTimeMillis() - origin;
	}
	
	public double lapAverage() {
		return 1D * lap_total / lap_cnt;
	}

	public void reset() {
		origin	  = -1;
		lap_total =  0;
		lap_cnt	  =  0;
		last_lap  =  0;
	}
}
