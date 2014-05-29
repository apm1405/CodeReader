package com.wkswind.codereader.fileexplorer.sort;

import java.io.File;
import java.util.Comparator;

//sorts based on a file or folder. folders will be listed first
public class SortName implements Comparator<File> {
	@Override
	public int compare(File f1, File f2) {
		return f1.getName().compareTo(f2.getName());
	}
}