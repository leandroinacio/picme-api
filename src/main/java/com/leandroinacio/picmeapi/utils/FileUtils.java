package com.leandroinacio.picmeapi.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.face.Face;
import com.leandroinacio.picmeapi.picture.Picture;
import com.leandroinacio.picmeapi.user.User;

public class FileUtils {

	public final static String XML_FACE_ANALYZER = "/home/leandroinacio/workspace/picme-api/src/main/resources/face/haarcascade-frontalface-alt.xml";
	public final static String ROOT_FOLDER = "/home/leandroinacio/workspace/picme-pics/";
	
	public static FilenameFilter getDefaultFilenameFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png");
			}
		};
	}
	
	public static void checkAndCreatePaths(String path) throws IOException {
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
	public static void copyToFolderAndRenameFile(MultipartFile file, String path, String newFileName) throws IOException {
		FileUtils.checkAndCreatePaths(path);
		Files.copy(file.getInputStream(), Paths.get(path, file.getOriginalFilename()));
		File oldName = new File(path + file.getOriginalFilename());
		File newName = new File(path + newFileName);
		oldName.renameTo(newName);
	}

	public static List<Mat> detectFace(File file) {
		
		// Get the image and convert to gray scale
		Mat facePicture = opencv_imgcodecs.imread(file.getAbsolutePath(), opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

		// Get the face detection standards
		@SuppressWarnings("resource")
		CascadeClassifier faceIdentifier = new CascadeClassifier(XML_FACE_ANALYZER);
		
		// Detect all faces on the given image
		RectVector facesDetected = new RectVector();
		faceIdentifier.detectMultiScale(facePicture, facesDetected, 1.1, 1, 0, new Size(160, 160), new Size(500, 500));
		
		// Create new mat list to return the faces resized and in gray scale
		List<Mat> facesCaptured = new ArrayList<>();
		for (Integer pos = 0; pos < facesDetected.size(); pos++) {
			Mat currentFace = new Mat(facePicture, facesDetected.get(pos));
			opencv_imgproc.resize(currentFace, currentFace, new Size(160, 160));
			facesCaptured.add(currentFace);
		}
		
		return facesCaptured;
	}
	
	/**
	 * @return the file name (id + file type)
	 */
	public static String getFileName(Long id, String fileType) {
		return id + "." + (fileType.split("/")[fileType.split("/").length - 1]);
	}
	
	/**
	 * TODO: Change the root folder, should not be hard coded
	 * @return the file path (root + user createDate + user Id)
	 */
	public static String getUserFacePath(User user) {
		return ROOT_FOLDER + "face/" + DateUtils.getYear(user.getCreateDate()) + "/" 
				+ DateUtils.getMonth(user.getCreateDate()) + "/" 
				+ DateUtils.getDayOfMonth(user.getCreateDate()) + "/" + user.getId() + "/"; 
	}

	public static String getPhotographerPicturePath(Picture picture) {
		return ROOT_FOLDER + "picture/photographer/"
				+ DateUtils.getYear(picture.getCreateDate()) + "/" + DateUtils.getMonth(picture.getCreateDate())
				+ "/" + DateUtils.getDayOfMonth(picture.getCreateDate()) + "/" 
				+ picture.getPhotographer().getId() + "/"; 
	}

	public static String getOwnerPicturePath(Picture picture) {
		return ROOT_FOLDER + "picture/owner/" + DateUtils.getYear(picture.getCreateDate()) 
				+ "/" + DateUtils.getMonth(picture.getCreateDate()) + "/" 
				+ DateUtils.getDayOfMonth(picture.getCreateDate()) + "/" 
				+ picture.getPhotographer().getId() + "/"; 
	}
	
	// TODO: Enum for the string type
	public static String getFaceYmlByUser(User user, String type) {
		return FileUtils.getUserFacePath(user) + type + ".yml";
	}
}
