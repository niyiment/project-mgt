package niyiment.projectmgt.project.api.dto;

import java.time.LocalDate;

public record ProjectRequest(String name, String description,
                             LocalDate startDate, LocalDate endDate, String status) {
}
