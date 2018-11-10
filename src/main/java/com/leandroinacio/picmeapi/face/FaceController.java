package com.leandroinacio.picmeapi.face;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/face")
public class FaceController {

	private static final Logger log = LoggerFactory.getLogger(FaceController.class);
	
	@Autowired
	private IFaceService faceService;
	
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PostMapping(value="/upload")
	public HttpStatus upload(@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) { return HttpStatus.PRECONDITION_FAILED; }
			faceService.upload(file);
			return HttpStatus.OK;
		} catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
		
	@GetMapping("/serveImage/{id}")
	public ResponseEntity<Resource> serveImage(@PathVariable("id") Long id) {
		return faceService.serveOneImage(id);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public HttpStatus delete(@PathVariable("id") Long id) {
		try {
			faceService.deleteImage(id);
			return HttpStatus.OK;
		}catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
