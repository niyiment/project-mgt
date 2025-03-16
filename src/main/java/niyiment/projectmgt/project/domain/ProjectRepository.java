package niyiment.projectmgt.project.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectRepository extends ReactiveCrudRepository<Project, Integer> {
    Flux<Project> findByName(String name);

    Flux<Project> findByNameContainingIgnoreCase(String name);

    Flux<Project> findByStatus(String status);

    @Query("SELECT * FROM projects WHERE " +
            "(:name IS NULL OR name ILIKE concat('%', :name, '%')) AND " +
            "(:status IS NULL OR status = :status) AND " +
            "(:startDate IS NULL OR start_date >= :startDate) AND " +
            "(:endDate IS NULL OR end_date <= :endDate) " +
            "ORDER BY created_at DESC " +
            "LIMIT :limit OFFSET :offset")
    Flux<Project> findProjectsWithFilters(
            String name,
            String status,
            String startDate,
            String endDate,
            int limit,
            int offset);

    @Query("SELECT COUNT(*) FROM projects WHERE " +
            "(:name IS NULL OR name ILIKE concat('%', :name, '%')) AND " +
            "(:status IS NULL OR status = :status) AND " +
            "(:startDate IS NULL OR start_date >= :startDate) AND " +
            "(:endDate IS NULL OR end_date <= :endDate)")
    Mono<Long> countProjectsWithFilters(
            String name,
            String status,
            String startDate,
            String endDate);
}
