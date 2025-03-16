package niyiment.projectmgt.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectFilterRequest {
    private String name;
    private String status;
    private String startDate;
    private String endDate;
    private int page = 0;
    private int size = 10;
}