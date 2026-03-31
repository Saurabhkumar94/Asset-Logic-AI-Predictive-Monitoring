package com.asset.service;

import com.asset.model.SystemMetrics;
import com.asset.repository.MetricsRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private MetricsRepository repository;

    public void generatePdfReport() {
        try {
            String dest = "Lab_Health_Report.pdf"; 
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Asset Logic AI - Lab Health Report").setBold().setFontSize(20));
            document.add(new Paragraph("Device Monitoring Summary\n\n"));

            float[] columnWidths = {150f, 100f, 200f};
            Table table = new Table(columnWidths);
            
            table.addCell("Device Name");
            table.addCell("CPU Usage (%)");
            table.addCell("Timestamp");

            List<SystemMetrics> metrics = repository.findAll();
            for (SystemMetrics m : metrics) {
                table.addCell(m.getDeviceName() != null ? m.getDeviceName() : "Unknown");
                table.addCell(String.format("%.2f", m.getCpuUsage()));
                table.addCell(m.getTimestamp().toString());
            }

            document.add(table);
            document.close();
            System.out.println("✅ PDF Report generated: " + dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}