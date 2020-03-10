package com.cldiaz.springreact.projmanagetool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueUsernameException extends RuntimeException{

		public UniqueUsernameException(String message) {
			super(message);
		}
	
	
	
}
