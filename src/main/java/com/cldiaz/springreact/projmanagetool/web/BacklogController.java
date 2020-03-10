package com.cldiaz.springreact.projmanagetool.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cldiaz.springreact.projmanagetool.domain.Backlog;
import com.cldiaz.springreact.projmanagetool.domain.Project;
import com.cldiaz.springreact.projmanagetool.domain.ProjectTask;
import com.cldiaz.springreact.projmanagetool.services.MapValidErrorService;
import com.cldiaz.springreact.projmanagetool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidErrorService mapValidErrorService; 
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, 
			BindingResult result, @PathVariable String backlog_id, Principal principal){
		
		ResponseEntity<?> errMap = mapValidErrorService.MapValidation(result);
		if(errMap != null) return errMap;
		
		ProjectTask projTask = projectTaskService.addProjectTesk(backlog_id, projectTask, principal.getName());
		
		return new ResponseEntity<ProjectTask>(projTask, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
		return projectTaskService.findBacklogById(backlog_id, principal.getName());
	}
	
	@GetMapping("/{backlog_id}/{ptId}")
	public ResponseEntity<?> geProjectTaskByProjSequence(@PathVariable String backlog_id, 
			@PathVariable String ptId, Principal principal){
		
		ProjectTask projectTask = projectTaskService.findProjTaskByProjSequence(backlog_id, ptId, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
		
	}
	
	@PatchMapping("/{backlog_id}/{ptId}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask, BindingResult result,
			@PathVariable String backlog_id, @PathVariable String ptId, Principal principal){
		
		ResponseEntity<?> errMap = mapValidErrorService.MapValidation(result);
		if(errMap != null) return errMap;
		
		ProjectTask updatedTask = projectTaskService.updateByProjSequence(updatedProjectTask, backlog_id, ptId, principal.getName());
		
		return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{backlog_id}/{ptId}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String ptId, Principal principal){
		
		projectTaskService.deleteProjectTask(backlog_id, ptId, principal.getName());
		
		return new ResponseEntity<String>("Project with ID: '" + ptId+"'was deleted.", HttpStatus.OK);
	}
	
}
