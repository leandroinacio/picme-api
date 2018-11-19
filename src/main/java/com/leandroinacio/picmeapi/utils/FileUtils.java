package com.leandroinacio.picmeapi.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtils {

	public static FilenameFilter getDefaultFilenameFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png");
			}
		};
	}
}
