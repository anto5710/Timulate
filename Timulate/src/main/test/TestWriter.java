package main.test;

public abstract class TestWriter<T, R> {
	
	public abstract Test<T, R> generateTest();
	
	public boolean correct(R response, R answer) {
		return answer == null ? answer == response : answer.equals(response);
	}
	
	public boolean correct(Test<T, R>test, R response) {
		return correct(response, test.getAnswer());
	}
}
