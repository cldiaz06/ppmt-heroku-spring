package com.cldiaz.springreact.projmanagetool.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cldiaz.springreact.projmanagetool.domain.Backlog;
import com.cldiaz.springreact.projmanagetool.domain.Project;
import com.cldiaz.springreact.projmanagetool.domain.User;
import com.cldiaz.springreact.projmanagetool.exception.ProjNotFoundException;
import com.cldiaz.springreact.projmanagetool.exception.ProjectIdException;
import com.cldiaz.springreact.projmanagetool.repositories.BacklogRepository;
import com.cldiaz.springreact.projmanagetool.repositories.ProjectRepository;
import com.cldiaz.springreact.projmanagetool.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projRespository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdatePrj(Project project, String username) {
		
		String projIdentifier = project.getProjectIdentifier().toUpperCase();
		
		if(project.getId() != null) {
			Project existProj = projRespository.findByProjectIdentifier(projIdentifier);
			if(( existProj != null) && (!existProj.getUser().getUsername().equals(username))) {
				throw new ProjNotFoundException("Project not found for your account.");
			}else if((existProj != null) && (!existProj.getId().equals(project.getId()))) {
				throw new ProjNotFoundException("Project with id " + projIdentifier + " doesn''t exist, thus no update executed.");
			}
			
		}
		
		try {
		
			User user = userRepository.findByUsername(username);
			
			project.setProjectIdentifier(projIdentifier);
			project.setUser(user);
			project.setProjectLeader(username);
					
			if(project.getId() == null) {
			
				Backlog log = new Backlog();
				project.setBacklog(log);
				log.setProject(project);
				log.setProjectIdentifier(projIdentifier);
			}
			
			if(project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(projIdentifier));
			}
			
			return projRespository.save(project);
			
		}catch(Exception x) {
			throw new ProjectIdException("Project ID '" +projIdentifier+"' already exists");
		}
	}
	
	public Project findProjectByIdentifier(String projectId, String username) {
		Project proj = projRespository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(proj == null) {
			throw new ProjectIdException("Project Id '" +projectId + "' does not exists");
		}
		
		if(!proj.getProjectLeader().equals(username)) {
			throw new ProjNotFoundException("Project not found for your account.");
		}
		
		return proj;
	}
	
	public Iterable<Project> findAllProjects(String username){
		return projRespository.findByProjectLeader(username);
	}
	
	public void deleteProjByIdentifier(String projectId, String username) {

		projRespository.delete(findProjectByIdentifier(projectId, username));
	}
	
	
	
}
