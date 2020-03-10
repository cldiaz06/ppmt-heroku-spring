package com.cldiaz.springreact.projmanagetool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjNotFoundException extends RuntimeException {

	public ProjNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
