package niyiment.projectmgt.report.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;
import niyiment.projectmgt.report.dto.ReportData;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static org.apache.poi.poifs.macros.Module.ModuleType.Document;

@Component("pdfReportGenerator")
@Slf4j
public class PdfReportGenerator implements ReportGenerator {

    @Override
    public Mono<byte[]> generateReport(ReportData reportData) {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,
                    16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,
                    14, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font bodyFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,
                    12);
            Paragraph title = new Paragraph("Report " , titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            float[] columnWidths = new float[reportData.getHeaders().size()];
            Arrays.fill(columnWidths, 1f);
            PdfPTable table = new PdfPTable(columnWidths);
            reportData.getHeaders()
                    .forEach(columnTitle -> {
                        PdfPCell headerCell = new PdfPCell();
                        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        headerCell.setBorderWidth(2);
                        headerCell.setPhrase(new Phrase(columnTitle, headerFont));
                        table.addCell(headerCell);
                    });

            reportData.getData().forEach(row ->
                    reportData.getHeaders().forEach(header -> table.addCell(row.getOrDefault(header, "").toString()))
            );

            document.add(table);
            document.close();
            return outputStream.toByteArray();
        });
    }

    @Override
    public String getContentType() {
        return "application/pdf";
    }

    @Override
    public String getFileExtension() {
        return ".pdf";
    }
}

