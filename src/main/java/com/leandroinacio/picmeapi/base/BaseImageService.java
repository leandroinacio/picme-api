package com.leandroinacio.picmeapi.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class BaseImageService extends BaseService {
	
	private static final Logger log = LoggerFactory.getLogger(BaseImageService.class);

	protected void checkAndCreatePaths(String path) throws IOException {
		if (Files.notExists(Paths.get(path))) {
			Files.createDirectories(Paths.get(path));
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param base the model class of the image
	 * @param newFileName
	 * @throws IOException
	 */
	protected void copyToFolderAndRenameFile(MultipartFile file, String path, String newFileName) throws IOException {
		Files.copy(file.getInputStream(), Paths.get(path, file.getOriginalFilename()));
		File oldName = new File(path + file.getOriginalFilename());
		File newName = new File(path + newFileName);
		oldName.renameTo(newName);
	}
		
}
