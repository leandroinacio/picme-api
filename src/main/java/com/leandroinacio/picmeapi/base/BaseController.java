package com.leandroinacio.picmeapi.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@ExceptionHandler
	protected HttpStatus handleException(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage());
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

//	@ExceptionHandler({ AccessDeniedException.class })
}
