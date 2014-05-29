package com.wkswind.codereader.synatax;

public interface DocumentHandler {
	public String getFileExtension();
	public String getFileMimeType();
	public String getFilePrettifyClass();
	public String getFileFormattedString(String fileString);
	public String getFileScriptFiles();
	
}
