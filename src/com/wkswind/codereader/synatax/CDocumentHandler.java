package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class CDocumentHandler implements DocumentHandler {

	@Override
	public String getFileExtension() {
		return "c";
	}

	@Override
	public String getFileFormattedString(String fileString) {
		return TextUtils.htmlEncode(fileString).replace("\n", "<br>");
	}

	@Override
	public String getFileMimeType() {
		return "text/html";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
