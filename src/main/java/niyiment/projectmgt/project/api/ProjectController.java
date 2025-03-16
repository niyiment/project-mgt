package niyiment.projectmgt.project.api;

import lombok.RequiredArgsConstructor;
import niyiment.projectmgt.project.api.dto.ProjectFilterRequest;
import niyiment.projectmgt.project.api.dto.ProjectRequest;
import niyiment.projectmgt.project.api.dto.ProjectResponse;
import niyiment.projectmgt.project.service.ProjectService;
import niyiment.projectmgt.shared.dto.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<ProjectResponse>> createProject(@RequestBody ProjectRequest request) {
        return projectService.createProject(request).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProjectResponse>> getProjectById(@PathVariable Integer id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<ResponseEntity<ProjectResponse>> getAllProjects() {
        return projectService.getAllProjects()
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProjectResponse>> updateProject(@PathVariable Integer id, @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id);
    }


    @PostMapping("/filter")
    public Mono<PageResponse<ProjectResponse>> getFilteredProjects(@RequestBody ProjectFilterRequest filterRequest) {
        return projectService.getFilteredProjects(filterRequest);
    }
}
