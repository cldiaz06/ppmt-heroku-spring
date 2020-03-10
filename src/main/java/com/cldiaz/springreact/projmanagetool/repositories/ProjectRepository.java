package com.cldiaz.springreact.projmanagetool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cldiaz.springreact.projmanagetool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	Project findByProjectIdentifier(String projIdentifier);
	
	@Override
	Iterable<Project> findAll();

	Iterable<Project> findByProjectLeader(String projectLeader);
	
}
