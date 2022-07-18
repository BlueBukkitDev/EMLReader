package dev.blue.eml.interpreter.data;

public class Raw extends Value {
	Double quantity;
	String contents;
	
	public Raw(double d) {
		quantity = d;
	}
	
	public Raw(String s) {
		contents = s;
	}
	
	public Object get() {
		return (contents == null) ? quantity : contents ;
	}
}
