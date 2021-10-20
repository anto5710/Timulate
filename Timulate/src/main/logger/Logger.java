package main.logger;

import java.io.PrintStream;

public class Logger {
	private String header;
	private String indenter;
	private String indenture;
	private String line_break;
	
	private int indent_level;
	
	private StreamType pstype;
	
	private enum StreamType {
		SYSTEM_OUT, SYSTEM_ERR;
	}
	
	public Logger(String header, String indenter, String line_break) {
		this.header = header;
		this.indenter = indenter;
		this.indenture = "s";
		this.indent_level = 0;
		this.line_break = line_break;
	
		streamOut();
	}
	
	public Logger(String header) {
		this(header, "\t", "\n");
	}
	
	public Logger() {
		this("");
	}
	
	public void print(Object msg) {
		PrintStream ps = getPrintStream();
		if(ps != null) {
			ps.print(getIndenture());
			ps.print(msg.toString());
		}
	}
	
	public void println(Object msg) {
		if(getIndentLevel() == 0) {
			printHeader();
		}
		print(msg.toString().concat(line_break));
	}
	
	public void printf(String format, Object ... args) {
		print(String.format(format, args)); 
	}
	
	public void streamOut() {
		this.pstype = StreamType.SYSTEM_OUT;
	}
	
	public void streamError() {
		this.pstype = StreamType.SYSTEM_ERR;
	}
	
	public PrintStream getPrintStream() {
		if(pstype == StreamType.SYSTEM_OUT) {
			return System.out;
		
		} else if(pstype == StreamType.SYSTEM_ERR) {
			return System.err;
		}
		
		return null;
	}
	
	public void setLineBreak(String line_break) {
		this.line_break = line_break;
	}
	
	public String getLineBreak() {
		return this.line_break;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public void printHeader() {
		PrintStream ps = getPrintStream();
		if(ps != null) {
			ps.print(getHeader());
		}
	}	

	public String getIndenture() {
		return this.indenture;
	}
	
	private void setIndenture(String indenture) {
		this.indenture = indenture;
	}
	
	public int getIndentLevel() {
		return indent_level;
	}
	
	public boolean setIndentLevel(int level) {
		if(level < 0) {
			return false;
		}
		
		indent_level = level;
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < getIndentLevel(); i++) {
			buffer.append(indenter);
		}
		setIndenture(indenture);
		
		return true;
	}
}
