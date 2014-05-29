package com.wkswind.codereader.fileexplorer;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

import android.webkit.MimeTypeMap;

public class CustomeFileFilter implements IOFileFilter {
	private static final String DOT = ".";
	private String codeType;
	public CustomeFileFilter(String codeType){
		this.codeType = codeType;
	}
	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		if(file.getAbsolutePath().startsWith(DOT)){
			return false;
		}
		if(MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath()).endsWith(codeType)){
			return true;
		}
		return false;
	}
	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return false;
	}
}
