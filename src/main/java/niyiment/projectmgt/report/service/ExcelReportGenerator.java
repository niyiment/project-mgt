package niyiment.projectmgt.report.service;

import lombok.extern.slf4j.Slf4j;
import niyiment.projectmgt.report.dto.ReportData;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;


@Slf4j
@Component("excelReportGenerator")
public class ExcelReportGenerator implements ReportGenerator {

    @Override
    public Mono<byte[]> generateReport(ReportData reportData) {
        return Mono.fromCallable(() -> {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(reportData.getReportName());

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < reportData.getHeaders().size(); i++) {
                headerRow.createCell(i).setCellValue(reportData.getHeaders().get(i));
            }

            for (int i = 0; i < reportData.getData().size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                Map<String, Object> rowData = reportData.getData().get(i);
                for (int j = 0; j < reportData.getHeaders().size(); j++) {
                    dataRow.createCell(j).setCellValue(rowData.getOrDefault(reportData.getHeaders().get(j), "").toString());
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return outputStream.toByteArray();
        });
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }

    @Override
    public String getFileExtension() {
        return ".xlsx";
    }
}
