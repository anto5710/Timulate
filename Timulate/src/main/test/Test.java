package main.test;

public class Test <T, R> {
	private T arg;
	private R answer;
	private boolean resolved;
	
	private Test(T arg, R answer, boolean resolved) {
		this.arg = arg;
		this.answer = answer;
		this.resolved = resolved;
	}
	
	public Test(T arg) {
		this(arg, null, false);
	}
	
	public Test(T arg, R answer) {
		this(arg, answer, true);
	}

	public T getArgument() {
		return arg;
	}

	public R getAnswer() {
		return answer;
	}
	
	public void setAnswer(R answer) {
		this.answer = answer;
		
		if (!isResolved()) {
			setResolved(true);
		}
	}
	
	private void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
	
	public boolean isResolved() {
		return resolved;
	}
}
