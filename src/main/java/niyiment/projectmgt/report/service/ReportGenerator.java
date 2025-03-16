package niyiment.projectmgt.report.service;

import niyiment.projectmgt.report.dto.ReportData;
import reactor.core.publisher.Mono;

public interface ReportGenerator {
    Mono<byte[]> generateReport(ReportData reportData);
    String getContentType();
    String getFileExtension();
}