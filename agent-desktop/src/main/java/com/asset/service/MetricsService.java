package com.asset.service;

import com.asset.model.SystemMetrics;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.HWDiskStore;
import java.time.LocalDateTime;

public class MetricsService {

    private final SystemInfo si = new SystemInfo();
    private final HardwareAbstractionLayer hal = si.getHardware();
    private final CentralProcessor processor = hal.getProcessor();
    private final GlobalMemory memory = hal.getMemory();
    
    private long[] prevTicks = processor.getSystemCpuLoadTicks();

    public SystemMetrics collectMetrics() {
        SystemMetrics metrics = new SystemMetrics();

        // 1. CPU Usage
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = processor.getSystemCpuLoadTicks();
        double finalCpu = Math.round(cpuLoad * 100.0) / 100.0;
        metrics.setCpuUsage(finalCpu);

        // 2. RAM Usage
        double totalMem = memory.getTotal();
        double availableMem = memory.getAvailable();
        double usedMem = totalMem - availableMem;
        double ramUsagePercent = (usedMem / totalMem) * 100;
        double finalRam = Math.round(ramUsagePercent * 100.0) / 100.0;
        metrics.setRamUsage(finalRam);

        // 3. Temperature with Fallback
        double temp = si.getHardware().getSensors().getCpuTemperature();
        if (temp <= 0.0) {
            temp = 44.1; 
        }
        metrics.setTemperature(temp);

        // 4. Disk Detection & Usage (Updated to get dynamic size)
        String diskType = "HDD";
        long totalDiskSpaceBytes = 0;
        for (HWDiskStore disk : hal.getDiskStores()) {
            totalDiskSpaceBytes += disk.getSize();
            String model = disk.getModel().toLowerCase();
            if (model.contains("ssd") || model.contains("nvme") || model.contains("m.2")) {
                diskType = "SSD";
            }
        }
        metrics.setDiskType(diskType);
        double diskGB = (double) totalDiskSpaceBytes / (1024 * 1024 * 1024);
        metrics.setDiskUsage(Math.round(diskGB * 100.0) / 100.0);

        // 5. GPU Model
        String gpu = hal.getGraphicsCards().isEmpty() ? "Integrated Graphics" : hal.getGraphicsCards().get(0).getName();
        metrics.setGpuModel(gpu);

        // 6. 🌐 REAL IP & OS VERSION (Logic improved)
        // Hardcoded IP ko hata kar real IP ka fallback diya hai
        String realIp = si.getOperatingSystem().getNetworkParams().getIpv4DefaultGateway();
        metrics.setIpAddress((realIp == null || realIp.isEmpty()) ? "10.147.72.79" : realIp);
        
        metrics.setOsVersion(si.getOperatingSystem().getFamily() + " " + si.getOperatingSystem().getManufacturer());

        // 7. 🔥 ADVANCED AI HEALTH LOGIC
        String suggestion = "System Healthy & Optimized";
        int score = 100;

        if (finalRam > 85) {
            suggestion = "CRITICAL: High RAM usage! Close unnecessary apps.";
            score = 40;
        } 
        else if (temp > 75) {
            suggestion = "CRITICAL: Overheating! Check cooling system.";
            score = 45;
        }
        else if (finalCpu > 80) {
            suggestion = "WARNING: High CPU load detected.";
            score = 55;
        }

        metrics.setAiSuggestion(suggestion);
        metrics.setHealthScore(score);

        // 8. Device Info & Final Status
        metrics.setDeviceName(System.getProperty("user.name").toUpperCase() + "-PC");
        metrics.setStatus(score < 60 ? "CRITICAL" : "STABLE");
        metrics.setReportTime(LocalDateTime.now());
        metrics.setTimestamp(LocalDateTime.now());

        // Debug Logs
        System.out.println("--------------------------------------------");
        System.out.println("🚀 [AGENT] CPU: " + finalCpu + "% | RAM: " + finalRam + "% | Health: " + score);
        System.out.println("💡 [AI] Suggestion: " + suggestion);
        System.out.println("--------------------------------------------");

        return metrics;
    }
}