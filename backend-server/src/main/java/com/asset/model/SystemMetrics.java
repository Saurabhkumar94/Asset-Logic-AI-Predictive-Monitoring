package com.asset.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_metrics")
public class SystemMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("cpuUsage")
    @Column(name = "cpu_usage")
    private Double cpuUsage;

    @JsonProperty("ramUsage")
    @Column(name = "ram_usage")
    private Double ramUsage;

    @JsonProperty("deviceName")
    private String deviceName; 

    @JsonProperty("status")
    private String status;     

    @JsonProperty("currentHash")
    private String currentHash; 

    @JsonProperty("previousHash")
    private String previousHash;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("temperature")
    @Column(name = "temperature")
    private Double temperature; 

    @JsonProperty("diskType")
    @Column(name = "disk_type") 
    private String diskType;    

    @JsonProperty("gpuModel")
    @Column(name = "gpu_model") 
    private String gpuModel;    

    @JsonProperty("reportTime")
    @Column(name = "report_time")
    private LocalDateTime reportTime;

    // 🔥 AI Prediction Fields
    @JsonProperty("healthScore")
    @Column(name = "health_score")
    private Integer healthScore; 
    
    @JsonProperty("prediction")
    @Column(name = "prediction")
    private String prediction; 

    // 🚀 NEW COLUMNS (Properly Mapped)
    @JsonProperty("ipAddress")
    @Column(name = "ip_address")
    private String ipAddress;

    @JsonProperty("diskUsage")
    @Column(name = "disk_usage")
    private Double diskUsage;

    @JsonProperty("osVersion")
    @Column(name = "os_version")
    private String osVersion;

    @JsonProperty("aiSuggestion")
    @Column(name = "ai_suggestion", columnDefinition = "TEXT") // Changed to TEXT for long messages
    private String aiSuggestion;

    @PrePersist
    protected void onCreate() { 
        timestamp = LocalDateTime.now(); 
        if (reportTime == null) {
            reportTime = LocalDateTime.now();
        }
    }

    // --- Standard Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(Double cpuUsage) { this.cpuUsage = cpuUsage; }

    public Double getRamUsage() { return ramUsage; }
    public void setRamUsage(Double ramUsage) { this.ramUsage = ramUsage; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCurrentHash() { return currentHash; } 
    public void setCurrentHash(String currentHash) { this.currentHash = currentHash; }

    public String getPreviousHash() { return previousHash; }
    public void setPreviousHash(String previousHash) { this.previousHash = previousHash; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public String getDiskType() { return diskType; }
    public void setDiskType(String diskType) { this.diskType = diskType; }

    public String getGpuModel() { return gpuModel; }
    public void setGpuModel(String gpuModel) { this.gpuModel = gpuModel; }

    public LocalDateTime getReportTime() { return reportTime; }
    public void setReportTime(LocalDateTime reportTime) { this.reportTime = reportTime; }

    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }

    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Double getDiskUsage() { return diskUsage; }
    public void setDiskUsage(Double diskUsage) { this.diskUsage = diskUsage; }

    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }

    public String getAiSuggestion() { return aiSuggestion; }
    public void setAiSuggestion(String aiSuggestion) { this.aiSuggestion = aiSuggestion; }
}