package com.leandroinacio.picmeapi.picture;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@RestController
@RequestMapping("/picture")
public class PictureController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PictureController.class);
	
	@Autowired
	private IPictureService pictureService;
	
	// TODO: Figure out this validation
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PostMapping(value="/upload")
	public BaseResponse upload(@RequestParam("picture") Picture picture, 
			@RequestParam("file") MultipartFile file) throws IOException {
		
		//TODO: Remove it, adding just for test sake.
		if (picture == null) { 
			User user = new User();
			user.setId((long)1);
			picture = new Picture("teste teste", "image/jpeg", "br", "mg", "Uberaba", 
					null, null, user, null, null, Calendar.getInstance(), null, null);
		}
		this.pictureService.upload(picture, file);
		return new BaseResponse();
	}

	@GetMapping("/serveOneImageById/{id}")
	public ResponseEntity<Resource> serveImage(@PathVariable("id") Long id) {
		Picture picture = this.pictureService.serveOneImageById(id);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(picture.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" 
						+ FileUtils.getFileName(picture.getId(), picture.getFileType()) + "\"")
				.body(picture.getFile());
	}
	
	@PostMapping("/addOwner")
	public BaseResponse addOwner(@RequestParam("picture") Picture picture, 
			@RequestParam("user") User user) throws IOException {
		this.pictureService.addOwner(picture, user);
		return new BaseResponse();
	}

	@GetMapping("/findByPhotographer/{page}/{size}/{id}") 
	public BaseResponse findByPhotographer(@PathVariable("page") Integer page, 
			@PathVariable("size") Integer size, @PathVariable("id") Long id) {
		return new BaseResponse(this.pictureService.findByPhotographer(new User() {{ setId(id);}}, page, size));
	}

	@GetMapping("/findByOwner/{page}/{size}/{id}") 
	public BaseResponse findByOwner(@PathVariable("page") Integer page, 
			@PathVariable("size") Integer size, @PathVariable("id") Long id) {
		return new BaseResponse(this.pictureService.findByOwner(new User() {{ setId(id);}}, page, size));
	}
	
	@DeleteMapping("/deleteById")
	public BaseResponse delete(@RequestParam("picture") Picture picture) throws IOException {
		
		// TODO: Add user when security is implemented
		this.pictureService.deleteImage(picture, null);
		return new BaseResponse();
	}
	
	@PostMapping("/searchPictures")
	public @ResponseBody List<Picture> searchPictures(@RequestParam("user") User user, 
			@RequestParam("locations") List<Location> locations) {
//		this.pictureService.
		return null;
		
	}
}
