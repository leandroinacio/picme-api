package com.leandroinacio.picmeapi.face;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@RestController @RequestMapping("/face")
public class FaceController extends BaseController {
		
	@Autowired
	private IFaceService faceService;
	
	// TODO: Figure out this validation, pass user to service and fill required data
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PostMapping(value="/upload")
	public BaseResponse upload(@RequestParam MultipartFile file, Authentication authentication) throws IOException {
		return new BaseResponse(faceService.upload(file));
	}
		
	@GetMapping("findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(faceService.findAll(page, size));
	}
	
	@GetMapping("findByUser/{page}/{size}/{userId}")
	public BaseResponse findAll(@PathVariable Integer page, 
			@PathVariable Integer size, @PathVariable Long userId) {
		
		// TODO: Get user from security
		User user = new User() {{ setId(userId); }};
		return new BaseResponse(faceService.findByUser(user, page, size));
	}
	
	@GetMapping("/serveOneImageById/{id}")
	public ResponseEntity<Resource> serveOneImageById(@PathVariable Long id) {
		Face face = faceService.serveOneImageById(id);
		return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(face.getFileType()))
		.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" 
				+ FileUtils.getFileName(face.getId(), face.getFileType()) + "\"")
		.body(face.getFile());
	}
	
	@DeleteMapping("/deleteById/{id}")
	public BaseResponse delete(@PathVariable Long id) throws IOException {
		faceService.deleteImage(id);
		return new BaseResponse();
	}
	
	// TODO: Validate if user has more than 2 files
	// TODO: Figure out how to reset base response
	@PostMapping("/train")
	public BaseResponse train(@RequestParam Long userId) {
		faceService.train(userId);
		return new BaseResponse();
	}
}
