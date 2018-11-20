package com.leandroinacio.picmeapi.utils;

import java.io.File;
import java.io.FilenameFilter;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class FileUtils {

	private final static String XML_FACE_ANALYZER = "/home/leandroinacio/workspace/picme-api/src/main/resources/face/haarcascade-frontalface-alt.xml";
	
	public static FilenameFilter getDefaultFilenameFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png");
			}
		};
	}
	
	public static Mat detectFace(File file) {
		
		// Get the image and convert to gray scale
		Mat facePicture = opencv_imgcodecs.imread(file.getAbsolutePath(), opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

		// Get the face detection standards
		@SuppressWarnings("resource")
		CascadeClassifier faceIdentifier = new CascadeClassifier(XML_FACE_ANALYZER);
		
		// Detect all faces on the given image
		RectVector faceDetected = new RectVector();
		faceIdentifier.detectMultiScale(facePicture, faceDetected, 1.1, 1, 0, new Size(160, 160), new Size(500, 500));
		
		// Create new mat to return just the face resized and in gray scale
		Mat faceCaptured = new Mat(facePicture, faceDetected.get(0));
		opencv_imgproc.resize(faceCaptured, faceCaptured, new Size(160, 160));
		return faceCaptured;
	}
}
