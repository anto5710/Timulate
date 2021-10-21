package main.test;

import main.util.Equator;

public abstract class TestWriter<T, R> {
	
	public abstract Test<T, R> generateTest();
	
	public boolean correct(R response, R answer) {
		return Equator.equalx(response, answer);
	}
	
	public boolean correct(Test<T, R>test, R response) {
		return correct(response, test.getAnswer());
	}
}
