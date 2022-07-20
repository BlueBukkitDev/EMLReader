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
	/**
	 *An exhaustive method which takes a given file and returns a {@code List<String>}, some of the contents of which may span multiple lines. Some characters and lines within the file will be omitted during this extraction. 
	 **/
	private List<String> readLines() {

		byte[] fileBytes;
		try {
			fileBytes = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		boolean building = false;
		String indexLine = "";
		String textChunk = "";
		boolean escapeNext = false;
		List<String> lines = new ArrayList<String>();
		for(byte b : fileBytes) {
			///////////////////////////////////////////////////////////////BUILDING
			if(building) {
				if(escapeNext) {//If we are escaping
					if(!(""+(char)b).equalsIgnoreCase("\"")) {
						//this should be when we have an escape without the "
						if(System.lineSeparator().contains(""+(char)b)) {//If you escaped and then created a new line
							indexLine += "\\";
							lines.add(indexLine);
							indexLine = "";
							escapeNext = false;
							continue;
						}
						//If you escaped but just did not create a new line and did not follow it with a "
						indexLine += "\\"+(char)b;
						//System.out.println("Idk why we did this");
						escapeNext = false;
						continue;
					}
					//This is when we encounter an escaped "
					textChunk += (char)b;
					escapeNext = false;
					continue;
				}
				//If we are not escaping
				if((""+(char)b).equalsIgnoreCase("\\")){
					escapeNext = true;
					continue;
				}

				//If we are building a chunk and are not escaping
				if((""+(char)b).equalsIgnoreCase("\"")) {//if we encounter an unescaped "
					//We need to end the chunk building
					indexLine += textChunk;
					textChunk = "";
					building = false;
					continue;
				}
				
				textChunk += (char)b;
				continue;
			}
			/////////////////////////////////////////////////////////////NOT BUILDING
			if((""+(char)b).equalsIgnoreCase("\"")) {//if we have encountered an unescaped "
				//this should begin a chunk of text as an object
				building = true;
				continue;
			}
			
			if (System.lineSeparator().contains(""+(char)b)) {//if the current char is a new line
				if(indexLine.length() > 0) {//if we're starting a new line
					if(indexLine.startsWith("|")) {//if it starts with a pipe
						indexLine = "";
						continue;
					}
					lines.add(indexLine);
					indexLine = "";
				}
				continue;
			} else {//if it's not a new line
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
