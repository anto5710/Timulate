package main.logger;

import java.io.PrintStream;
import java.util.Stack;

/**
 * A standard PrintStream buffer that supports nested headers and soft indentations.<br>
 * Designed to provide a quick way to print "[header]: result"<br>
 * Indentations are accumulated into a stack, enabling quick in/decrementing of indenter.
 * 
 * @author anto5710
 */
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
	
	/**
	 * Prints object with a master header if at the start of command line (zero indentation),
	 * then briefly cancels the header's indentation.
	 * @param obj The object to be printed.
	 */
	public void println(Object obj) {
		if (getIndentLevel() == 0){
			printMaster();
		}
		
		print(obj);
		dedentln();
	}
	
	/**
	 * Prints object, separating it to multiple string lines if necessary.
	 * Each line break will provoke an indentation at new line.
	 * 
	 * @param obj The object to be printed.
	 */
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
	
	/**
	 * Prints header and object, separating it to multiple string lines if necessary.
	 * Each line break will provoke an indentation at new line to match the header length.
	 * 
	 * @param obj The object to be printed.
	 */
	public void print(Object obj, String header) {
		printHeader(header);		
		print(obj);
	}
	
	/**
	 * Prints header and object with a master header if at the start of command line (zero indentation),
	 * then briefly cancels the header and master header's indentation.
	 *
	 * @param obj The object to be printed.
	 */
	public void println(Object obj, String header) {
		if (getIndentLevel() == 0){
			printHeader(master_header);
		}
			printHeader(header);
				print(obj);
			dedent();

		dedentln();
	}
	
	/**
	 * Prints the master header and adds corresponding indenter.
	 */
	protected void printMaster() {
		printHeader(master_header);
	}
	
	/**
	 * Prints the given header and adjust indenture accordingly such that the next line will begin at the end of the header.
	 * @param header
	 */
	protected void printHeader(String header) {
		PrintStream ps = getPrintStream();
		
		if (ps != null && header != null && !header.isEmpty()) {
			ps.print(header);
			indentTo(header);
		}
	}
	
	/**
	 * Prints a set line separator and starts the new line with the accumulated indentation, if any.
	 */
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

	/**
	 * Returns current indentation as string.
	 * @return The concatenation of all indenter in the indent stack.
	 */
	public String getIndenture() {
		return indent_str;
	}
	
	/**
	 * Clears all indentation.
	 */
	public void dedentAll() {
		indent_stack.clear();
		updateIndenture();
	}
	
	/**
	 * Clears all indentation and then prints an non-indented fresh new line.
	 */
	public void dedentAllln() {
		dedentAll();
		endl();
	}
	
	/**
	 * Decrements the last indentation and prints a new line with the remaining indentation, if any.
	 */
	public void dedentln() {
		dedent();
		endl();
	}
	
	protected void updateIndenture() {
		indent_str = "";
		indent_stack.forEach(s -> indent_str += s);
	}
	
	/**
	 * The amount of indentations currently stacked.
	 * @return The indentation count.
	 */
	public int getIndentLevel() {
		return indent_stack.size();
	}
	
	/**
	 * Indents by the length of the header such that next line will start at the end of the header.
	 * Tab characters or any non-singular whitespace characters are also accounted for.
	 * 
	 * @param header The string to indent next line to.
	 */
	public void indentTo(String header) {
		String indenter = "";
		
		for (char c : header.toCharArray()) {
			indenter += (Character.isWhitespace(c) ? c : ' ');
		}
		
		indent(indenter);
	}
	
	/**
	 * Indents whitespace characters <i>length</i> times.
	 * The whitespace characters will be added as a single indenter.
	 * 
	 * @param length The length of a new indenter
	 * @return If indentation was successful.
	 */
	public boolean indentSpaces(int length) {
		String spaces = "";
		
		for (int i = 0; i < length; i++) {
			spaces += space;
		}
		
		return indent(spaces);
	}
	
	/**
	 * Adds indenter to the indent stack, which will be update at once and applied since the next call of endl().
	 * @param indenter The string indenter
	 * @return If indenter was successfully added to the indent stack
	 */
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
	
	/**
	 * Removes the last indenter element from the indent stack.
	 */
	public void dedent() {
		if (!indent_stack.isEmpty()) {
			indent_stack.pop();
		}
		updateIndenture();
	}
	
	/**
	 * Sets the current PrintStream as System.out.
	 */
	public void setStreamOut() {
		this.pstype = StreamType.SYSTEM_OUT;
	}
	
	/**
	 * Sets the current PrintStream as System.err;
	 */
	public void setStreamError() {
		this.pstype = StreamType.SYSTEM_ERR;
	}
	
	/**
	 * Returns the latest instance of the current PrintStream type, which is either System.out or System.err;
	 * @return An instance of the current standard PrintStream.
	 */
	public PrintStream getPrintStream() {
		if(pstype == StreamType.SYSTEM_OUT) {
			return System.out;
		
		} else if(pstype == StreamType.SYSTEM_ERR) {
			return System.err;
		}
		
		return null;
	}
}
