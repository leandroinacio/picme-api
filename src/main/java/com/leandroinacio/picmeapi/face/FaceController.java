package com.leandroinacio.picmeapi.face;

import java.io.IOException;
import java.util.Calendar;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.user.User;

@RestController
@RequestMapping("/face")
public class FaceController extends BaseController {
	
	@Autowired
	private IFaceService faceService;
	
	// TODO: Figure out this validation
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PostMapping(value="/upload")
	public HttpStatus upload(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) { return HttpStatus.PRECONDITION_FAILED; }
		faceService.upload(file);
		return HttpStatus.OK;
	}
		
	@GetMapping("/serveImage/{id}")
	public ResponseEntity<Resource> serveImage(@PathVariable("id") Long id) {
		return faceService.serveOneImage(id);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public HttpStatus delete(@PathVariable("id") Long id) throws IOException {
			faceService.deleteImage(id);
			return HttpStatus.OK;
	}
	
	@PostMapping("/train")
	public HttpStatus train(Long userId, String date) {
		// TODO: Think about string to date conversion here, of just keep it string should also work
		faceService.train(userId, Calendar.getInstance());
		return HttpStatus.OK;
	}
}
