package niyiment.projectmgt.report.service;


import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import niyiment.projectmgt.report.dto.ReportData;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Component("csvReportGenerator")
@Slf4j
public class CsvReportGenerator implements ReportGenerator {

    @Override
    public Mono<byte[]> generateReport(ReportData reportData) {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(reportData.getHeaders().toArray(new String[0]));
            for (Map<String, Object> row : reportData.getData()) {
                String[] rowData = reportData.getHeaders().stream()
                        .map(header -> row.getOrDefault(header, "").toString())
                        .toArray(String[]::new);
                csvWriter.writeNext(rowData);
            }
            csvWriter.close();

            return outputStream.toByteArray();
        });
    }

    @Override
    public String getContentType() {
        return "text/csv";
    }

    @Override
    public String getFileExtension() {
        return ".csv";
    }

}
