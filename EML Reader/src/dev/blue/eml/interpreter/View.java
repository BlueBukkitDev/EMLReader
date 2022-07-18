package dev.blue.eml.interpreter;

import java.util.HashMap;

import dev.blue.eml.interpreter.data.Value;

public class View {
	
	HashMap<String, Value> vars;
	
	public View() {
		vars = new HashMap<String, Value>();
		
	}
}
