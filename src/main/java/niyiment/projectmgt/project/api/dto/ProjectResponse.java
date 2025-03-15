package niyiment.projectmgt.project.api.dto;

import java.time.LocalDate;

public record ProjectResponse(Integer id, String name, String description,
                              LocalDate startDate, LocalDate endDate, String status) {
}
