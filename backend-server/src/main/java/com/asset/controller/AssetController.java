package com.asset.controller;

import com.asset.model.SystemMetrics;
import com.asset.repository.MetricsRepository;
import com.asset.service.ReportService;
import com.asset.service.AIService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    private MetricsRepository metricsRepository;

    @Autowired
    private ReportService reportService; 

    @Autowired
    private AIService aiService; 

    // 🔥 FIX: Agent agar /save ya /report kisi par bhi bhejega, ye receive karega
    @PostMapping({"/save", "/report"})
    public SystemMetrics saveMetrics(@RequestBody SystemMetrics newEntry) {
        
        // 1. Audit Security: Pichle record ka hash nikalna
        Optional<SystemMetrics> lastEntry = metricsRepository.findTopByOrderByIdDesc();
        String prevHash = lastEntry.map(SystemMetrics::getCurrentHash).orElse("0");
        newEntry.setPreviousHash(prevHash);
        
        // 2. Hardware Debug Logging: Taaki Backend Terminal mein data dikhe
        System.out.println("--------------------------------------------");
        System.out.println("✅ Received from: " + newEntry.getDeviceName());
        System.out.println("🌡️ Temp: " + newEntry.getTemperature() + "°C");
        System.out.println("💾 Disk: " + newEntry.getDiskType() + " | GPU: " + newEntry.getGpuModel());
        System.out.println("--------------------------------------------");

        // 3. AI Health Analysis: Score calculate karna
        newEntry = aiService.analyzeHealth(newEntry);

        // 4. Save to MySQL
        return metricsRepository.save(newEntry);
    }

    @GetMapping("/all")
    public List<SystemMetrics> getAllMetrics() {
        return metricsRepository.findAll();
    }

    @GetMapping("/generate-report")
    public String downloadReport() {
        reportService.generatePdfReport();
        return "✅ PDF Report generated in backend-server folder! Check your files.";
    }
}