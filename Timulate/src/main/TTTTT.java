package main;

import main.logger.GenericLogger;
import main.logger.Logger;
import main.test.Test;
import main.test.TestWriter;
import main.test.Timable;
import main.timulator.Timulator;

public class TTTTT {
	public static void main(String[] args) {
		Logger p = new GenericLogger("[PLOG]: ");
		
		int [][][] m1 = {{{2}, {2,3}},
						{{2,3},{2,2},{23}},{{23},{22,3},{32,32,3},{2}}};
		int [][] m = {{1,2,3,32,3}, {1,2,3}};

		Timable<Object[], Integer> t = new Timable<Object[], Integer>() {

			@Override
			public String getName() {
				return "Solver1";
			}

			@Override
			public Integer respond(Object[] arg) {
				return -10;
			}
		};
		
		Timable<Object[], Integer> t2 = new Timable<Object[], Integer>() {

			@Override
			public String getName() {
				return "Solver2";
			}

			@Override
			public Integer respond(Object[] arg) {
				return -10;
			}
		};
		
		Timulator<Timable<Object[],Integer>, Object[], Integer> tim = 
				new Timulator<>(new TestWriter<Object[], Integer>() {

			@Override
			public Test<Object[], Integer> generateTest() {
				return new Test<Object[], Integer>(null, 10);
			}
		});
		tim.setFailBreak(true);
		tim.add(t, t2);
		tim.timulate();
		
//		p.setStreamError();;
//		p.println(m1, "[TEst]: ");
//		p.endl();
//		p.println(m, "[This Mat]: ");
//		p.print(m);
	}
}
