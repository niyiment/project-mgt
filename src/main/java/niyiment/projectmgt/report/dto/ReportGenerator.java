package niyiment.projectmgt.report.dto;

import reactor.core.publisher.Mono;

public interface ReportGenerator {
    Mono<Byte> generateReport(ReportData reportData);
    String getContentType();
    String getFileExtension();
}