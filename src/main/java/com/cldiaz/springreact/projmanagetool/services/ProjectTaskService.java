package com.cldiaz.springreact.projmanagetool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cldiaz.springreact.projmanagetool.domain.Backlog;
import com.cldiaz.springreact.projmanagetool.domain.Project;
import com.cldiaz.springreact.projmanagetool.domain.ProjectTask;
import com.cldiaz.springreact.projmanagetool.exception.ProjNotFoundException;
import com.cldiaz.springreact.projmanagetool.repositories.BacklogRepository;
import com.cldiaz.springreact.projmanagetool.repositories.ProjectRepository;
import com.cldiaz.springreact.projmanagetool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRep;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepo;
	
	@Autowired 
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;
	
	public ProjectTask addProjectTesk(String projectIdentifier,ProjectTask projectTask, String username) {
		
		try {
			//Backlog backlog = backlogRep.findByProjectIdentifier(projectIdentifier);
			
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
			projectTask.setBacklog(backlog);
			
			Integer backLogSeq = backlog.getPTSequence();
			backLogSeq++;
			backlog.setPTSequence(backLogSeq);
			
			projectTask.setProjSequence(projectIdentifier + "-"+ backLogSeq);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			if(projectTask.getPriority() == null || projectTask.getPriority().equals(0) ) {
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus() == null || projectTask.getStatus().isEmpty()) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepo.save(projectTask);
			
		} catch (Exception e) {
			throw new ProjNotFoundException("Project Not Found");
		}
		
		
	}

	public Iterable <ProjectTask> findBacklogById(String backlog_id, String username) {
		
		//Project proj = projectRepository.findByProjectIdentifier(backlog_id);
		Project proj = projectService.findProjectByIdentifier(backlog_id, username);
		if(proj ==null) {
			throw new ProjNotFoundException("Project with id: "+ backlog_id +" doesn''t exists.");
		}
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	public ProjectTask findProjTaskByProjSequence(String backlog_id,String pt_id, String username) {
		
		projectService.findProjectByIdentifier(backlog_id, username);
		
//		Backlog backlog = backlogRep.findByProjectIdentifier(backlog_id);
//		
//		if(backlog == null) {
//			throw new ProjNotFoundException("Project with id: "+ backlog_id +" doesn''t exists.");
//		}
		
		ProjectTask projectTask = projectTaskRepo.findByProjSequence(pt_id);
		
		if(projectTask == null) {
			throw new ProjNotFoundException("Project task with id: "+ pt_id +" doesn''t exists.");
		}
		
//		if(!projectTask.getBacklog().getProjectIdentifier().equals(backlog.getProjectIdentifier())) {
//			throw new ProjNotFoundException("Project with id: "+ pt_id+" doesn''t exists in project " + backlog_id);
//		}
		
		return projectTask;
	}
	
	public ProjectTask updateByProjSequence(ProjectTask updateTask, String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findProjTaskByProjSequence(backlog_id, pt_id, username);
		
		if(projectTask == null) {
			throw new ProjNotFoundException("Project task with id: "+ pt_id+" doesn''t exists.");
		}
		
		projectTask = updateTask;
		
		return projectTaskRepo.save(projectTask);
		
	}
	
	public void deleteProjectTask(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findProjTaskByProjSequence(backlog_id, pt_id, username);
		
		if(projectTask == null) {
			throw new ProjNotFoundException("Project task with id: "+ pt_id+" doesn''t exists.");
		}
		
		projectTaskRepo.delete(projectTask);
	}

}
