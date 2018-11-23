package com.leandroinacio.picmeapi.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
		
	@ExceptionHandler
	public BaseResponse handleException(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage());
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setData(null);
		baseResponse.setErrorMessage("Something went wrong.");
		baseResponse.setIsError(true);
		return baseResponse;
	}

}
