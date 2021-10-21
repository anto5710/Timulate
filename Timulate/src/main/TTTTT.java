package main;

import main.logger.PLogger;

public class TTTTT {
	public static void main(String[] args) {
		PLogger p = new PLogger();
		
		int [][][] m1 = {{{2}, {2,3}},
						{{2,3},{2,2},{23}},{{23},{22,3},{32,32,3},{2}}};
		int [][] m = {{1,2,3,32,3}, {1,2,3}};

		p.println(m1, "[TEst]: ");
		p.endl();
		p.print(m, "[This Mat]: ");
		p.print(m);
	}
}
