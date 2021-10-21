package main.logger;

import java.io.PrintStream;
import java.util.Stack;

public class Logger {
	
	protected Stack<String> indent_stack = new Stack<>();
	private   StreamType	pstype		 = StreamType.SYSTEM_OUT;

	protected String indent_str		= "";
	private   String master_header	= null;
	protected String line_separator	= "\n";
	protected String space			= " ";
	
	private enum StreamType {
		SYSTEM_OUT, SYSTEM_ERR;
	}
		
	public Logger(String master_header) {
		this.master_header = master_header;
		setStreamOut();
	}
	
	public Logger() {
		this(null);
	}
	
	public void println(Object obj) {
		if (getIndentLevel() == 0){
			printMaster();
		}
		
		print(obj);
		dedentAllln();
	}
	
	public void print(Object obj) {
		PrintStream ps = getPrintStream();
		if (ps == null) {
			return;
		}
		
		String obj_str = String.valueOf(obj);
		String [] lines = obj_str.split(line_separator);
		
		if (lines.length == 0) {
			ps.print(obj_str);
			
		} else {
			ps.print(lines[0]);
			
			for (int i = 1; i < lines.length; i++) {
				endl();
				ps.print(lines[i]);
			}
		}
	}
	
	public void printfln(String format, String header, Object ... args) {
		if (getIndentLevel() == 0){
			printMaster();
		}
		
		printf(format, header, args);
		dedentln();
	}
	
	public void printf(String format, String header, Object ... args) {
		printHeader(header);
		print(String.format(format, args));
	}
	
	public void print(Object obj, String header) {
		printHeader(header);		
		print(obj);
	}
	
	public void println(Object obj, String header) {
		if (getIndentLevel() == 0){
			printHeader(master_header);
		}
		
		printHeader(header);
		print(obj);
		dedentAllln();
	}
	
	protected void printMaster() {
		printHeader(master_header);
	}
	
	protected void printHeader(String header) {
		PrintStream ps = getPrintStream();
		
		if (ps != null && header != null && !header.isEmpty()) {
			ps.print(header);
			indentTo(header);
		}
	}
	
	public void endl() {
		PrintStream ps = getPrintStream();
		if (ps == null) {
			return;
		}
		
		ps.print(line_separator);
		
		if(getIndentLevel() > 0) {
			ps.print(getIndenture());
		}
	}
	
	public String getMasterHeader() {
		return this.master_header;
	}

	public void setMasterHeader(String header) {
		this.master_header = header;
	}

	public String getIndenture() {
		return indent_str;
	}
	
	public void dedentAll() {
		indent_stack.clear();
		updateIndenture();
	}
	
	public void dedentAllln() {
		dedentAll();
		endl();
	}
	
	public void dedentln() {
		dedent();
		endl();
	}
	
	protected void updateIndenture() {
		indent_str = "";
		indent_stack.forEach(s -> indent_str += s);
	}
	
	public int getIndentLevel() {
		return indent_stack.size();
	}
	
	public void indentTo(String header) {
		String indenter = "";
		
		for (char c : header.toCharArray()) {
			indenter += (Character.isWhitespace(c) ? c : ' ');
		}
		
		indent(indenter);
	}
	
	public boolean indentSpaces(int length) {
		String spaces = "";
		
		for (int i = 0; i < length; i++) {
			spaces += space;
		}
		
		return indent(spaces);
	}
	
	public boolean indent(String indenter) {
		return indent(indenter, 1);
	}
	
	public boolean indent(String indenter, int amount) {
		boolean all_added = true;
		
		for (int i = 0; i < amount; i++) {
			if (0 < indenter.length() && !indent_stack.add(indenter)) {
				all_added = false;
			}
		}
		
		updateIndenture();
		
		return all_added;
	}
	
	public void dedent() {
		if (!indent_stack.isEmpty()) {
			indent_stack.pop();
		}
		updateIndenture();
	}
	
	public void setStreamOut() {
		this.pstype = StreamType.SYSTEM_OUT;
	}
	
	public void setStreamError() {
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
}
