package niyiment.projectmgt.project.api.mapper;

import niyiment.projectmgt.project.api.dto.ProjectRequest;
import niyiment.projectmgt.project.api.dto.ProjectResponse;
import niyiment.projectmgt.project.domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toProject(ProjectRequest request);

    ProjectResponse toProjectResponse(Project project);
}
