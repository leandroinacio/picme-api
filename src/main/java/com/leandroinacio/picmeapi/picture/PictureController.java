package com.leandroinacio.picmeapi.picture;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@RestController @RequestMapping("/picture")
public class PictureController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PictureController.class);
	
	@Autowired
	private IPictureService pictureService;
	
	// TODO: Figure out this validation
	//consumes = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	@PreAuthorize("hasAuthority('CREATE_PICTURE')")
	@PutMapping(value="/upload")
	public BaseResponse upload(@RequestParam Picture picture, 
			@RequestParam MultipartFile file, Authentication auth) throws IOException {
		
		//TODO: Remove it, adding just for test sake.
		if (picture == null) { 
			Location location = new Location();
			location.setId((long)1);

			JwtUser jwtUser = (JwtUser) auth.getPrincipal();
			User user = new User(jwtUser);
			
			picture = new Picture();
			picture.setDescription("teste teste");
			picture.setFileType("image/jpeg");
			picture.setPhotographer(user);
			picture.setOwner(Arrays.asList(user));
			picture.setLocation(location);
		}
		
		this.pictureService.upload(picture, file, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}

	@PreAuthorize("hasAuthority('SERVE_USER_PICTURE')")
	@GetMapping("/serveOneImageById/{id}")
	public ResponseEntity<Resource> serveImage(@PathVariable Long id, Authentication auth) {
		Picture picture = this.pictureService.serveOneImageById(id, (JwtUser) auth.getPrincipal());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(picture.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" 
						+ FileUtils.getFileName(picture.getId(), picture.getFileType()) + "\"")
				.body(picture.getFile());
	}
	
	@PreAuthorize("hasAuthority('ADD_OWNER_PICTURE')")
	@PostMapping("/addOwner")
	public BaseResponse addOwner(@RequestParam Picture picture, 
			Authentication auth) throws IOException {
		this.pictureService.addOwner(picture, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}

	@PreAuthorize("hasAuthority('FIND_PHOTOGRAPHER_PICTURE')")
	@GetMapping("/findByPhotographer/{page}/{size}") 
	public BaseResponse findByPhotographer(@PathVariable Integer page, 
			@PathVariable Integer size, @PathVariable Long id) {
		return new BaseResponse(this.pictureService.findByPhotographer(new User() {{ setId(id);}}, page, size));
	}

	@PreAuthorize("hasAuthority('FIND_OWNER_PICTURE')")
	@GetMapping("/findByOwner/{page}/{size}") 
	public BaseResponse findByOwner(@PathVariable Integer page, 
			@PathVariable Integer size, Authentication auth) {
		return new BaseResponse(this.pictureService.findByOwner((JwtUser) auth.getPrincipal(), page, size));
	}
	
	@PreAuthorize("hasAuthority('DELETE_USER_PICTURE')")
	@DeleteMapping("/deleteById")
	public BaseResponse delete(@RequestParam Picture picture, Authentication auth) throws IOException {		
		this.pictureService.deleteImage(picture, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}
	
	@PreAuthorize("hasAuthority('SEARCH_PICTURES')")
	@PostMapping("/searchPictures")
	public BaseResponse searchPictures(@RequestBody List<Location> locations, Authentication auth) {
		
		BaseResponse baseResponse = new BaseResponse(this.pictureService.searchPicturesAndAnalyze((JwtUser) auth.getPrincipal(), locations));
		return baseResponse;
		
	}
}
