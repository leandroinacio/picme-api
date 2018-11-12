package com.leandroinacio.picmeapi.picture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/picture")
public class PictureController {

	private static final Logger log = LoggerFactory.getLogger(PictureController.class);
	
	@Autowired
	private IPictureService pictureService;
	
	// TODO: Figure out this validation
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PutMapping(value="/value")
	public HttpStatus upload(@RequestParam("picture") Picture picture, @RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) { return HttpStatus.PRECONDITION_FAILED; }
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
}
