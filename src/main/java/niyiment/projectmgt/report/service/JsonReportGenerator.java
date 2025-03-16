package niyiment.projectmgt.report.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import niyiment.projectmgt.report.dto.ReportData;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component("jsonReportGenerator")
@Slf4j
@RequiredArgsConstructor
public class JsonReportGenerator implements ReportGenerator {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<byte[]> generateReport(ReportData reportData) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(reportData).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getFileExtension() {
        return ".json";
    }
}

