package niyiment.projectmgt.project.service;


import lombok.RequiredArgsConstructor;
import niyiment.projectmgt.project.api.dto.ProjectRequest;
import niyiment.projectmgt.project.api.dto.ProjectResponse;
import niyiment.projectmgt.project.api.mapper.ProjectMapper;
import niyiment.projectmgt.project.domain.Project;
import niyiment.projectmgt.project.domain.ProjectRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Mono<ProjectResponse> createProject(ProjectRequest request) {
        Project project = ProjectMapper.INSTANCE.toProject(request);
        Mono<Project> savedProject = projectRepository.save(project);

        return savedProject.map(ProjectMapper.INSTANCE::toProjectResponse);
    }

    public Mono<ProjectResponse> getProjectById(Integer id) {
        return projectRepository.findById(id)
                .map(ProjectMapper.INSTANCE::toProjectResponse);
    }

    public Flux<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .map(ProjectMapper.INSTANCE::toProjectResponse);
    }

    public Mono<ProjectResponse> updateProject(Integer id, ProjectRequest request) {
        return projectRepository.findById(id)
                .flatMap(project -> {
                    project = ProjectMapper.INSTANCE.toProject(request);
                    return projectRepository.save(project);
                })
                .map(ProjectMapper.INSTANCE::toProjectResponse);
    }

    public Mono<Void> deleteProject(Integer id) {
        return projectRepository.deleteById(id);
    }


}
