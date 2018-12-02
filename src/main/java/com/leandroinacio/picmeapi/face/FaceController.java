package com.leandroinacio.picmeapi.face;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.utils.FileUtils;

@RestController @RequestMapping("/face")
public class FaceController extends BaseController {
		
	@Autowired
	private IFaceService faceService;
	
	// TODO: Figure out this validation
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PreAuthorize("hasAuthority('CREATE_FACE')")
	@PostMapping(value="/upload")
	public BaseResponse upload(@RequestParam MultipartFile file, Authentication auth) throws IOException {
		return new BaseResponse(faceService.upload(file, (JwtUser) auth.getPrincipal()));
	}
		
	@PreAuthorize("hasAuthority('VIEW_ALL_FACES')")
	@GetMapping("findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(faceService.findAll(page, size));
	}

	@PreAuthorize("hasAuthority('VIEW_USER_FACES')")
	@GetMapping("findByUser/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, 
			@PathVariable Integer size, Authentication auth) {
		return new BaseResponse(faceService.findByUser((JwtUser) auth.getPrincipal(), page, size));
	}
	
	@PreAuthorize("hasAuthority('SERVE_USER_FACES')")
	@GetMapping("/serveOneImageById/{id}")
	public ResponseEntity<Resource> serveOneImageById(@PathVariable Long id, Authentication auth) {
		Face face = faceService.serveOneImageById(id, (JwtUser) auth.getPrincipal());
		return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(face.getFileType()))
		.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" 
				+ FileUtils.getFileName(face.getId(), face.getFileType()) + "\"")
		.body(face.getFile());
	}
	
	@PreAuthorize("hasAuthority('DELETE_USER_FACES')")
	@DeleteMapping("/deleteById/{id}")
	public BaseResponse delete(@PathVariable Long id, Authentication auth) throws IOException {
		faceService.deleteImage(id, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}
	
	// TODO: Validate if user has more than 2 files
	@PreAuthorize("hasAuthority('TRAIN_FACES')")
	@PostMapping("/train")
	public BaseResponse train(Authentication auth) {
		faceService.train((JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}
}
