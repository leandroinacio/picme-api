package com.leandroinacio.picmeapi.picture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@Service
public class PictureService implements IPictureService {

	@Autowired
	private IPictureRepository pictureRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Picture upload(Picture picture, MultipartFile file, JwtUser jwtUser) throws IOException {
		
		// Setup missing values from picture and save to the db
		picture.setPhotographer(new User(jwtUser));
		picture.setFileType(file.getContentType());		
		picture = this.pictureRepository.save(picture);
		
		// Copy to folder and rename it
		FileUtils.copyToFolderAndRenameFile(file, FileUtils.getPicturePath(picture), 
				FileUtils.getFileName(picture.getId(), picture.getFileType()));
		
		// TODO: Create demo picture and folder.
		
		return picture;
	}
	
	public void addOwner(Picture picture, JwtUser jwtUser) throws IOException {
		
		// Add new user as owner of the picture
		picture.getOwner().add(new User(jwtUser));
		this.pictureRepository.save(picture);
		
		// Move file to new owner folder
		Files.copy(
				Paths.get(FileUtils.getPicturePath(picture), 
						FileUtils.getFileName(picture.getId(), picture.getFileType())), 
				Paths.get(FileUtils.getPicturePath(picture), 
						FileUtils.getFileName(picture.getId(), picture.getFileType()))
			);
	}
	
	public void deleteImage(Picture picture, JwtUser jwtUser) throws IOException {
		
		// Fetch all pictue data and remove photographer or owner who did the request
		picture = this.pictureRepository.findById(picture.getId());
		
		if (picture.getPhotographer().getId() == jwtUser.getId()) {
			picture.setPhotographer(null);
		}else {
			for (User owner : picture.getOwner()) {
				if (owner.getId() == jwtUser.getId()) {
					picture.getOwner().remove(owner);
				}
			}
		}
	
		// If everybody deleted, remove it from picture db and delete from folder
		if (picture.getPhotographer() == null && picture.getOwner().isEmpty()) {
			this.pictureRepository.deleteById(picture.getId());
			Files.deleteIfExists(Paths.get(FileUtils.getPicturePath(picture), 
			FileUtils.getFileName(picture.getId(), picture.getFileType())));
			
			// TODO: Remove demo file
			// Files.deleteIfExists(Paths.get(this.getDemoFilePath(picture), this.getFileName(picture)));
		}
	}
	
	public Picture serveOneImageById(Long id, JwtUser jwtUser) {
		Picture picture = this.pictureRepository.findById(id);
		Boolean isOwnerOrPhotographer = picture.getPhotographer().getId() == jwtUser.getId();
		
		if (!isOwnerOrPhotographer) { 
			for (User owner : picture.getOwner()) {
				if (owner.getId()==jwtUser.getId()) {
					isOwnerOrPhotographer = true;
					break;
				}
			}
		}
		
		if (isOwnerOrPhotographer) {
			String filePath = FileUtils.getPicturePath(picture) 
					+ FileUtils.getFileName(picture.getId(), picture.getFileType());
			picture.setFile(resourceLoader.getResource("file:" + filePath));
		}
		return picture;
	}
	
	public Page<Picture> findByPhotographer(User photographer, Integer page, Integer size) {
		return this.pictureRepository.findByPhotographer(photographer, PageRequest.of(page, size));
	}

	public Page<Picture> findByOwner(JwtUser jwtUser, Integer page, Integer size) {
		return this.pictureRepository.findByOwner(new User(jwtUser), PageRequest.of(page, size));
	}
			
	public List<Picture> searchPicturesAndAnalyze(JwtUser jwtUser, List<Location> locations) {
		
		User user = new User(jwtUser);
		
		// Find pictures that match locations
		// TODO: Update findByLocation to retrieve all locations
		List<Picture> pictures = this.pictureRepository.findByLocation(locations.get(0));
		
		List<Picture> pictureMatchingFaces = new ArrayList<>();
		
		// Go through pictures and try to recognize faces
		for (Picture picture : pictures) {
			
			File file = new File(FileUtils.getPicturePath(picture) 
					+ FileUtils.getFileName(picture.getId(), picture.getFileType()));
			
			// Get faces on pictures
			List<Mat> facesToIdentify = FileUtils.detectFace(file);
		
			// Check for faces
			IntPointer label1 = new IntPointer(0);
			IntPointer label2 = new IntPointer(0);
			IntPointer label3 = new IntPointer(0);
			DoublePointer confidence1 = new DoublePointer(0);
			DoublePointer confidence2 = new DoublePointer(0);
			DoublePointer confidence3 = new DoublePointer(0);
			FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
	        FaceRecognizer fisherfaces = FisherFaceRecognizer.create();
	        FaceRecognizer lbph = LBPHFaceRecognizer.create();
	
			// Get faces recognition for user				
			//TODO: Work on logic here, add other face recognition methods and get user from security instead of fetch
			user = userRepository.findById(user.getId());
			
			eigenfaces.read(FileUtils.getFaceYmlByUser(user, "eigen"));
			fisherfaces.read(FileUtils.getFaceYmlByUser(user, "fisher"));
			lbph.read(FileUtils.getFaceYmlByUser(user, "lbph"));
			
			for (Mat currentFace : facesToIdentify) {
				eigenfaces.predict(currentFace, label1, confidence1);			
				fisherfaces.predict(currentFace, label2, confidence2);			
				lbph.predict(currentFace, label3, confidence3);			
			}

			// TODO: Remove these hard coded values
			if (confidence1.get() <= 3600 || confidence2.get() <= 350 || confidence3.get() <= 42 ) {
				pictureMatchingFaces.add(picture);
			}
			
			System.out.println(file.getName());
			System.out.println("eigen: " + confidence1.get() + " / " + label1.get());
			System.out.println("fisher: " + confidence2.get() + " / " + label2.get());
			System.out.println("lbph: " + confidence3.get() + " / " + label3.get());
			System.out.println("------------------------------------------------------");
		}
		
		/**
		 * TODO: Conclusao:
		 * usar confidence pra determinar o que vamos retornar, assim como ja esta
		 * quanto menor o confidence, maior a certeza de que o a face eh a mesma que estamos comparando.
		 * label nao esta batendo, mesmo que retorna uns ids bem diferentes. precisamos entender a logica aqui.
		 */
		
		return pictureMatchingFaces;
	}
	
}
