package com.asset.service;

import com.asset.model.SystemMetrics;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PdfService {

    public File generateMetricsReport(List<SystemMetrics> metrics) throws Exception {
        File file = new File("System_Health_Report.pdf");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("AssetLogic AI - System Health Report").setFontSize(20).setBold());
        document.add(new Paragraph("Generated for Device: " + (metrics.isEmpty() ? "N/A" : metrics.get(0).getDeviceName())));
        document.add(new Paragraph("\n"));

        Table table = new Table(4);
        table.addCell("ID");
        table.addCell("CPU Usage (%)");
        table.addCell("RAM Usage (%)");
        table.addCell("AI Prediction");

        for (SystemMetrics m : metrics) {
            table.addCell(String.valueOf(m.getId()));
            table.addCell(String.valueOf(m.getCpuUsage()));
            table.addCell(String.valueOf(m.getRamUsage()));
            table.addCell(m.getPrediction());
        }

        document.add(table);
        document.close();
        return file;
    }
}