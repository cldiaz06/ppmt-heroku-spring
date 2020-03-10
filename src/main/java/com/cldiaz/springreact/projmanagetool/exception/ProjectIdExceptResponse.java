package com.cldiaz.springreact.projmanagetool.exception;


public class ProjectIdExceptResponse {
	
	private String projectIdentifier;

	public ProjectIdExceptResponse(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	

}
