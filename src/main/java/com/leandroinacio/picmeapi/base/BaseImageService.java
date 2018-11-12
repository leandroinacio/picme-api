package com.leandroinacio.picmeapi.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.utils.DateUtils;

public class BaseImageService extends BaseService {
	
	private static final Logger log = LoggerFactory.getLogger(BaseImageService.class);
	protected static final String UPLOAD_ROOT = "/home/leandroinacio/workspace/picme-pics";

	protected void checkAndCreatePaths(BaseEntity base) throws IOException {
		if (Files.notExists(Paths.get(this.getFilePath(base))) || Files.notExists(Paths.get(this.getDemoFilePath(base)))) {
			Files.createDirectories(Paths.get(this.getFilePath(base)));
			Files.createDirectories(Paths.get(this.getDemoFilePath(base)));
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param base the model class of the image
	 * @param newFileName
	 * @throws IOException
	 */
	protected void copyToFolderAndRenameFile(MultipartFile file, BaseEntity base, String newFileName) throws IOException {
		Files.copy(file.getInputStream(), Paths.get(this.getFilePath(base), file.getOriginalFilename()));
		File oldName = new File(this.getFilePath(base) + file.getOriginalFilename());
		File newName = new File(this.getFilePath(base) + newFileName);
		oldName.renameTo(newName);
	}
	
	/**
	 * TODO: Check if folder should be based on create date for picture classes
	 * @param face object
	 * @return the file path (root + create date of the image)
	 */
	protected String getFilePath(BaseEntity base) {
		return UPLOAD_ROOT + "/original/" + DateUtils.getDayMonthYear(base.getCreateDate()) + "/"; 
	}

	/**
	 * @param face object
	 * @return the demonstration file path (root + create date of the image)
	 */
	protected String getDemoFilePath(BaseEntity base) {
		return UPLOAD_ROOT + "/demo/" + DateUtils.getDayMonthYear(base.getCreateDate()); 
	}	
}
