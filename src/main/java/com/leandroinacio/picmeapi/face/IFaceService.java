package com.leandroinacio.picmeapi.face;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFaceService {
	public Face upload(Face face, MultipartFile file) throws IOException;
	public void deleteImage(Face face) throws IOException;
	public Resource serveOneImage(Face face);
}
