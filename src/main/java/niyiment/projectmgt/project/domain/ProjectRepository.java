package niyiment.projectmgt.project.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProjectRepository extends ReactiveCrudRepository<Project, Integer> {
    Flux<Project> findByName(String name);
}
