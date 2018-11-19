package com.leandroinacio.picmeapi.picture;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;

@RestController
@RequestMapping("/picture")
public class PictureController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PictureController.class);
	
	@Autowired
	private IPictureService pictureService;
	
	// TODO: Figure out this validation
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PostMapping(value="/upload")
	public HttpStatus upload(@RequestParam("picture") Picture picture, @RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) { return HttpStatus.PRECONDITION_FAILED; }
			//TODO: Remove it, adding just for test sake.
			if (picture == null) { 
				User user = new User();
				user.setId((long)1);
				picture = new Picture("teste teste", "image/jpeg", "br", "mg", "Uberaba", null, null, user, null, null, Calendar.getInstance());
			}
			pictureService.upload(picture, file);
			return HttpStatus.OK;
		} catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@GetMapping("/serveImage/{id}")
	public ResponseEntity<Resource> serveImage(@PathVariable("id") Long id) {
		return pictureService.serveOneImage(id);
	}

	@GetMapping("/serveDemoImage/{id}")
	public ResponseEntity<Resource> serveDemoImage(@PathVariable("id") Long id) {
		return pictureService.serveOneDemoImage(id);
	}
	
	@PostMapping("/addOwner")
	public HttpStatus addOwner(Picture picture, User user) throws IOException {
		pictureService.addOwner(picture, user);
		return HttpStatus.OK;
	}
	
	@GetMapping("/findByPhotographer/{id}") 
	public List<Picture> findByPhotographer(@PathVariable("id") Long id) {
		return pictureService.findByPhotographer(new User() {{ setId(id);}});
	}
	
	@DeleteMapping("/deleteById/{id}")
	public HttpStatus delete(@PathVariable("id") Long id) {
		try {
			pictureService.deleteImage(id);
			return HttpStatus.OK;
		} catch (Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	@PostMapping("/searchPictures")
	public @ResponseBody List<Picture> searchPictures(User user, Location[] locations) {
//		pictureService.
		return null;
		
	}
}
