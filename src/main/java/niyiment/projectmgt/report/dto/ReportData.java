package niyiment.projectmgt.report.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportData {
    private String reportName;
    private List<String> headers;
    private List<Map<String, Object>> data;
}