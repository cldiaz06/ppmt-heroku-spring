package com.cldiaz.springreact.projmanagetool.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cldiaz.springreact.projmanagetool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	
	ProjectTask findByProjSequence(String projSequence);
	
	
	
}
