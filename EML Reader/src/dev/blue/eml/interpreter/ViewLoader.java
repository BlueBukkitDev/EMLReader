package dev.blue.eml.interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ViewLoader {
	private File file;
	
	public ViewLoader(File file) throws IOException {
		if(file == null) {
			throw new NullPointerException();
		}
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		String[] parts = file.getName().split(".");
		if(!parts[parts.length].equalsIgnoreCase("eml")) {
			throw new IOException("Invalid File Extension");
		}
		if(file.isDirectory()) {
			throw new IOException("File must not be a directory");
		}
		
		this.file = file;
	}
}
