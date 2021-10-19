package main.test;

public class Test <T, R> {
	private T arg;
	private R answer;
	
	public Test(T arg, R answer) {
		this.arg = arg;
		this.answer = answer;
	}

	public T getArgument() {
		return arg;
	}

	public R getAnswer() {
		return answer;
	}
}
