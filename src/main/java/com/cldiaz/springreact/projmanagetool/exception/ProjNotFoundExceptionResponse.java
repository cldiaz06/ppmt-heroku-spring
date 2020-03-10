package com.cldiaz.springreact.projmanagetool.exception;

public class ProjNotFoundExceptionResponse {
	
	private String ProjectNotFound;

	public ProjNotFoundExceptionResponse(String projectNotFound) {
		super();
		ProjectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		ProjectNotFound = projectNotFound;
	}
	
	
}
