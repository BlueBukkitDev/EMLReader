package dev.blue.eml.interpreter;

import java.util.HashMap;

import dev.blue.eml.interpreter.data.Raw;
import dev.blue.eml.interpreter.data.Tag;
import dev.blue.eml.interpreter.data.Value;

public class View {
	
	private HashMap<String, Raw> raws;
	private HashMap<String, Tag> tags;
	
	HashMap<String, Value> vars;
	HashMap<String, Value> customs;
	
	public View() {
		vars = new HashMap<String, Value>();
		customs = new HashMap<String, Value>();
		
		raws = new HashMap<String, Raw>();
		tags = new HashMap<String, Tag>();
	}
	
	public void addRaw(String name, Raw raw) {
		raws.put(name, raw);
	}
	
	public void addTag(String name, Tag tag) {
		tags.put(name, tag);
	}
	
	public HashMap<String, Raw> getRaws() {
		return raws;
	}
	
	public HashMap<String, Tag> getTags() {
		return tags;
	}
}
