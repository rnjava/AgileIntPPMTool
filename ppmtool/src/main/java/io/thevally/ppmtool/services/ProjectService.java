package io.thevally.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.thevally.ppmtool.domain.Project;
import io.thevally.ppmtool.exceptions.ProjectIdException;
import io.thevally.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
		}
	}
	
	public Project findProjectByIdentifier(String projectid) {
		
		Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectid+"' does not exist");
		}
		
		return project; 
	}
	
	public Iterable<Project> findAllProjects(){
		
		return projectRepository.findAll();
		
	}
	
	public void deleteProjectByIdentifier(String projectid) {
		Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Cannot Delete Project with ID '"+projectid+"'. This project does not exist.");
		}
		
		projectRepository.delete(project);
	}
}
