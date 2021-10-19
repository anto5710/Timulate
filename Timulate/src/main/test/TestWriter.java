package main.test;

public abstract class TestWriter<T, R> {
	
	public abstract Test<T, R> generateTest();
	
	public boolean mark(R response, R answer) {
		return answer == null ? answer == response : answer.equals(response);
	}
	
	public boolean mark(Test<T, R>test, R response) {
		return mark(response, test.getAnswer());
	}
}
