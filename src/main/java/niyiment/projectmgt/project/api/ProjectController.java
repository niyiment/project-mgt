package niyiment.projectmgt.project.api;

import lombok.RequiredArgsConstructor;
import niyiment.projectmgt.project.api.dto.ProjectRequest;
import niyiment.projectmgt.project.api.dto.ProjectResponse;
import niyiment.projectmgt.project.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequestMapping("/api/projects")
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProjectResponse> createProject(@RequestBody ProjectRequest request) {
        return projectService.createProject(request);
    }

    @GetMapping("/{id}")
    public Mono<ProjectResponse> getProjectById(@PathVariable Integer id) {
        return projectService.getProjectById(id);
    }

    @GetMapping
    public Flux<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/{id}")
    public Mono<ProjectResponse> updateProject(@PathVariable Integer id, @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id);
    }
}
