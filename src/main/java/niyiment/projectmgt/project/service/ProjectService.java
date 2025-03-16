package niyiment.projectmgt.project.service;


import lombok.RequiredArgsConstructor;
import niyiment.projectmgt.project.api.dto.ProjectFilterRequest;
import niyiment.projectmgt.project.api.dto.ProjectRequest;
import niyiment.projectmgt.project.api.dto.ProjectResponse;
import niyiment.projectmgt.project.api.mapper.ProjectMapper;
import niyiment.projectmgt.project.domain.Project;
import niyiment.projectmgt.project.domain.ProjectRepository;
import niyiment.projectmgt.project.exception.ProjectNotFoundException;
import niyiment.projectmgt.shared.dto.PageResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "project", key = "#id")
    public Mono<ProjectResponse> getProjectById(Integer id) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("Project not found with id: " + id)))
                .map(ProjectMapper.INSTANCE::toProjectResponse);
    }

    @Cacheable(value = "projects")
    public Flux<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .map(ProjectMapper.INSTANCE::toProjectResponse);
    }

    @CacheEvict(value = "project", key = "#id")
    public Mono<ProjectResponse> updateProject(Integer id, ProjectRequest request) {
        return projectRepository.findById(id)
                .flatMap(project -> {
                    project = ProjectMapper.INSTANCE.toProject(request);
                    return projectRepository.save(project);
                })
                .map(ProjectMapper.INSTANCE::toProjectResponse)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("Project not found with id: " + id)));
    }

    public Mono<Void> deleteProject(Integer id) {
        return projectRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("Project not found with id: " + id)));
    }

    public Mono<PageResponse<ProjectResponse>> getFilteredProjects(ProjectFilterRequest filterRequest) {
        int page = filterRequest.getPage();
        int size = filterRequest.getSize();

        Flux<ProjectResponse> projectsFlux = projectRepository.findProjectsWithFilters(
                filterRequest.getName(),
                filterRequest.getStatus(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate(),
                size,
                page * size
        ).map(ProjectMapper.INSTANCE::toProjectResponse);

        Mono<Long> countMono = projectRepository.countProjectsWithFilters(
                filterRequest.getName(),
                filterRequest.getStatus(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate()
        );

        return Mono.zip(projectsFlux.collectList(), countMono)
                .map(tuple -> {
                    var projects = tuple.getT1();
                    var count = tuple.getT2();
                    return new PageResponse<>(
                            projects,
                            page,
                            size,
                            count,
                            (long) Math.ceil((double) count / size)
                    );
                });
    }


}
