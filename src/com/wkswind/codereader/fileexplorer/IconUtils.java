/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wkswind.codereader.fileexplorer;

import java.io.File;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.webkit.MimeTypeMap;

import com.wkswind.codereader.R;

public class IconUtils {

	private static HashMap<String, Integer> sMimeIcons = new HashMap<String, Integer>();

	private static void add(String mimeType, int resId) {
		if (sMimeIcons.put(mimeType, resId) != null) {
			throw new RuntimeException(mimeType + " already registered!");
		}
	}

	static {
		int icon;
		// Package
		icon = R.drawable.ic_doc_apk;
		add("application/vnd.android.package-archive", icon);

		// Audio
		icon = R.drawable.ic_doc_audio;
		add("audio/mpeg", icon);
		add("audio/aac", icon);
		add("audio/wav", icon);
		add("audio/ogg", icon);
		add("audio/midi", icon);
		add("audio/x-ms-wma", icon);
		add("audio/x-ms-wax", icon);
        add("audio/amr", icon);
        add("audio/basic", icon);
        add("audio/x-mpegurl", icon);
        add("audio/x-pn-realaudio", icon);
        add("audio/x-realaudio", icon);
		

		// Source code
        
		icon = R.drawable.ic_doc_codes;
		add("application/rdf+xml", icon);
		add("application/rss+xml", icon);
		add("application/x-object", icon);
		add("application/xhtml+xml", icon);
		add("text/css", icon);
		add("text/html", icon);
		add("text/xml", icon);
		add("text/x-c++hdr", icon);
		add("text/x-c++src", icon);
		add("text/x-chdr", icon);
		add("text/x-csrc", icon);
		add("text/x-dsrc", icon);
		add("text/x-csh", icon);
		add("text/x-haskell", icon);
		add("text/x-java", icon);
		add("text/x-literate-haskell", icon);
		add("text/x-pascal", icon);
		add("text/x-tcl", icon);
		add("text/x-tex", icon);
		add("application/x-latex", icon);
		add("application/x-texinfo", icon);
		add("application/atom+xml", icon);
		add("application/ecmascript", icon);
		add("application/json", icon);
		add("application/javascript", icon);
		add("application/xml", icon);
		add("text/javascript", icon);
		add("application/x-javascript", icon);
        add("text/h323", icon);
        add("text/iuls", icon);
        add("text/mathml", icon);
		

		// Compressed
		icon = R.drawable.ic_doc_compressed;
		add("application/mac-binhex40", icon);
		add("application/rar", icon);
		add("application/zip", icon);
		add("application/x-apple-diskimage", icon);
		add("application/x-debian-package", icon);
		add("application/x-gtar", icon);
		add("application/x-iso9660-image", icon);
		add("application/x-lha", icon);
		add("application/x-lzh", icon);
		add("application/x-lzx", icon);
		add("application/x-stuffit", icon);
		add("application/x-tar", icon);
		add("application/x-webarchive", icon);
		add("application/x-webarchive-xml", icon);
		add("application/gzip", icon);
		add("application/x-7z-compressed", icon);
		add("application/x-deb", icon);
		add("application/x-rar-compressed", icon);

		// Font
		icon = R.drawable.ic_doc_font;
		add("application/x-font", icon);
		add("application/font-woff", icon);
		add("application/x-font-woff", icon);
		add("application/x-font-ttf", icon);
		// Image
		icon = R.drawable.ic_doc_image;
		add("image/bmp", icon);
        add("image/gif", icon);
        add("image/ico", icon);
        add("image/ief", icon);
        add("image/jpeg", icon);
        add("image/pcx", icon);
        add("image/png", icon);
        add("image/svg+xml", icon);
        add("image/tiff", icon);
        add("image/vnd.djvu", icon);
        add("image/vnd.wap.wbmp", icon);
        add("image/x-cmu-raster", icon);
        add("image/x-coreldraw", icon);
        add("image/x-coreldrawpattern", icon);
        add("image/x-coreldrawtemplate", icon);
        add("image/x-corelphotopaint", icon);
        add("image/x-icon", icon);
        add("image/x-jg", icon);
        add("image/x-jng", icon);
        add("image/x-ms-bmp", icon);
        add("image/x-photoshop", icon);
        add("image/x-portable-graymap", icon);
        add("image/x-portable-pixmap", icon);
        add("image/x-rgb", icon);
        add("image/x-xbitmap", icon);
        add("image/x-xpixmap", icon);
        add("image/x-xwindowdump", icon);

		// PDF
		icon = R.drawable.ic_doc_pdf;
		add("application/pdf", icon);

		// Presentation
		icon = R.drawable.ic_doc_presentation;
		add("application/vnd.ms-powerpoint", icon);
		add("application/vnd.openxmlformats-officedocument.presentationml.presentation",
				icon);
		add("application/vnd.openxmlformats-officedocument.presentationml.template",
				icon);
		add("application/vnd.openxmlformats-officedocument.presentationml.slideshow",
				icon);
		add("application/vnd.stardivision.impress", icon);
		add("application/vnd.sun.xml.impress", icon);
		add("application/vnd.sun.xml.impress.template", icon);
		add("application/x-kpresenter", icon);
		add("application/vnd.oasis.opendocument.presentation", icon);

		// Spreadsheet
		icon = R.drawable.ic_doc_spreadsheet;
		add("application/vnd.oasis.opendocument.spreadsheet", icon);
		add("application/vnd.oasis.opendocument.spreadsheet-template", icon);
		add("application/vnd.ms-excel", icon);
		add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				icon);
		add("application/vnd.openxmlformats-officedocument.spreadsheetml.template",
				icon);
		add("application/vnd.stardivision.calc", icon);
		add("application/vnd.sun.xml.calc", icon);
		add("application/vnd.sun.xml.calc.template", icon);
		add("application/x-kspread", icon);

		// Text
		icon = R.drawable.ic_doc_text;
		add("text/plain",icon);
		add("application/vnd.oasis.opendocument.text", icon);
		add("application/vnd.oasis.opendocument.text-master", icon);
		add("application/vnd.oasis.opendocument.text-template", icon);
		add("application/vnd.oasis.opendocument.text-web", icon);
		add("application/msword", icon);
		add("application/vnd.openxmlformats-officedocument.wordprocessingml.document",
				icon);
		add("application/vnd.openxmlformats-officedocument.wordprocessingml.template",
				icon);
		add("application/vnd.stardivision.writer", icon);
		add("application/vnd.stardivision.writer-global", icon);
		add("application/vnd.sun.xml.writer", icon);
		add("application/vnd.sun.xml.writer.global", icon);
		add("application/vnd.sun.xml.writer.template", icon);
		add("application/x-abiword", icon);
		add("application/x-kword", icon);

		// Video
		icon = R.drawable.ic_doc_video;
		add("application/x-quicktimeplayer", icon);
		add("application/x-shockwave-flash", icon);
		add("video/3gpp", icon);
        add("video/dl", icon);
        add("video/dv", icon);
        add("video/fli", icon);
        add("video/m4v", icon);
        add("video/mpeg", icon);
        add("video/mp4", icon);
        add("video/quicktime", icon);
        add("video/vnd.mpegurl", icon);
        add("video/x-la-asf", icon);
        add("video/x-mng", icon);
        add("video/x-ms-asf", icon);
        add("video/x-ms-wm", icon);
        add("video/x-ms-wmv", icon);
        add("video/x-ms-wmx", icon);
        add("video/x-ms-wvx", icon);
        add("video/x-msvideo", icon);
        add("video/x-sgi-movie", icon);
	}	
	
	public static Drawable loadMimeIcon(Context context, File file){
		final Resources res = context.getResources();
		if(file.isDirectory()){
			return res.getDrawable(R.drawable.ic_root_folder);
		}
		
		String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		Integer resId = sMimeIcons.get(mimeType);
		
		if (resId != null) {
			return res.getDrawable(resId);
		}

		if (mimeType == null) {
			// TODO: generic icon?
			return res.getDrawable(R.drawable.ic_doc_generic);
		}

		// Otherwise look for partial match
		final String typeOnly = mimeType.split("/")[0];
		if ("audio".equals(typeOnly)) {
			return res.getDrawable(R.drawable.ic_doc_audio);
		} else if ("image".equals(typeOnly)) {
			return res.getDrawable(R.drawable.ic_doc_image);
		} else if ("text".equals(typeOnly)) {
			return res.getDrawable(R.drawable.ic_doc_text);
		} else if ("video".equals(typeOnly)) {
			return res.getDrawable(R.drawable.ic_doc_video);
		} else {
			return res.getDrawable(R.drawable.ic_doc_generic);
		}

	}
}
