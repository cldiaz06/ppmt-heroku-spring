package com.cldiaz.springreact.projmanagetool.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cldiaz.springreact.projmanagetool.domain.Project;
import com.cldiaz.springreact.projmanagetool.services.MapValidErrorService;
import com.cldiaz.springreact.projmanagetool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjController {

	@Autowired
	private ProjectService projService;
	
	@Autowired
	private MapValidErrorService mapValidErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project proj, BindingResult result, Principal principal){
		
		ResponseEntity<?> errorMap = mapValidErrorService.MapValidation(result);
		if(errorMap != null) return errorMap;

		Project project = projService.saveOrUpdatePrj(proj, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
		Project proj = projService.findProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<Project>(proj, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal principal){
		return projService.findAllProjects(principal.getName());
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProj(@PathVariable String projectId, Principal principal){
		projService.deleteProjByIdentifier(projectId, principal.getName());
		
		return new ResponseEntity<String>("Project with ID: '" + projectId+"'was deleted.", HttpStatus.OK);
	}
	
}
