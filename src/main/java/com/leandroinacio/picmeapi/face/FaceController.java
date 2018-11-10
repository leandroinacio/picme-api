package com.leandroinacio.picmeapi.face;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
//	@PostMapping("/upload")
//	public HttpStatus upload(@RequestParam MultipartFile file) {
//		try {
//			faceService.save(face);
//			return HttpStatus.OK;
//		} catch(Exception e) {
//			log.error(e.getMessage());
//			return HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//	}
		
//	@DeleteMapping("/delete/{id}")
//	public HttpStatus delete(@PathVariable("id") Long id) {
//		try {
//			faceService.deleteById(id);
//			return HttpStatus.OK;
//		}catch(Exception e) {
//			log.error(e.getMessage());
//			return HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//	}
}
