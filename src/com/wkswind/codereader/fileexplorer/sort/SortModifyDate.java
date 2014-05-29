package com.wkswind.codereader.fileexplorer.sort;

import java.io.File;
import java.util.Comparator;

//sorts based on a file or folder. folders will be listed first
public class SortModifyDate implements Comparator<File> {
	@Override
	public int compare(File f1, File f2) {
		/*if ((f1.isDirectory() && f2.isDirectory())
				|| (!f1.isDirectory() && !f2.isDirectory()))
			return f1.getName().compareTo(f2.getName());
		else if (f1.isDirectory() && !f2.isDirectory())
			return -1;
		else
			return 1;*/
		return (int)(f1.lastModified() - f2.lastModified());
	}
}