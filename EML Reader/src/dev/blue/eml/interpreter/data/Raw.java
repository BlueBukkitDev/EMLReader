package dev.blue.eml.interpreter.data;

public abstract class Raw extends Value {
	protected Double quantity;
	protected String contents;
	
	public static Raw newRaw(String s) {
		try {
			double value = Double.parseDouble(s);
			return new Number(value);
		} catch(NumberFormatException e) {
			return new Text(s);
		}
	}

	public Object get() {
		return (contents == null) ? quantity : contents;
	}
}
