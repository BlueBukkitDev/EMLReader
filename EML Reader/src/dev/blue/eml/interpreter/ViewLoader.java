package dev.blue.eml.interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import dev.blue.eml.interpreter.data.Raw;

public class ViewLoader {
	private File file;
	
	public ViewLoader(File file) throws IOException {
		if(file == null) {
			throw new NullPointerException();
		}
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		String[] parts = file.getPath().split("\\.");
		if(!parts[parts.length-1].equalsIgnoreCase("eml")) {
			throw new IOException("Invalid File Extension");
		}
		if(file.isDirectory()) {
			throw new IOException("File must not be a directory");
		}
		
		this.file = file;
	}
	
	public View loadView() {
		View view = new View();
		boolean building = false;
		for(String each : readLines()) {
			if(each.startsWith("var")) {
				loadRawFromLine(each, view);
			}
		}
		
		for(String each:view.getRaws().keySet()) {
			System.out.println(each+": "+view.getRaws().get(each).get());
		}
		
		return null;
	}
	
	private void loadRawFromLine(String line, View view) {
		String[] components = line.split("\\|");
		try {
			Raw var = Raw.newRaw(components[2]);
			view.addRaw(components[1], var);
			return;
		} catch(ArrayIndexOutOfBoundsException e) {
			view.addRaw(components[1], Raw.newRaw(""));
			return;
		}
	}
	
	private List<String> readLines() {

		byte[] fileBytes;
		try {
			fileBytes = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String indexLine = "";
		List<String> lines = new ArrayList<String>();
		for(byte b : fileBytes) {
			if (System.lineSeparator().contains(""+(char)b)) {
				if(indexLine.length() > 0) {
					if(indexLine.startsWith("|")) {
						indexLine = "";
						continue;
					}
					lines.add(indexLine);
					indexLine = "";
				}
				continue;
			} else {
				indexLine += (char)b;
			}
		}
//		
//		System.out.println("Lines ("+lines.size()+")"+":");
//		for(String each:lines) {
//			System.out.println(each);
//		}
		
		return lines;
	}
}
