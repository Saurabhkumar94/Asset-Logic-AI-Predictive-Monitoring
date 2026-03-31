package com.asset.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class SystemMetrics {
    private Long id;

    @JsonProperty("cpuUsage")
    private Double cpuUsage;

    @JsonProperty("ramUsage")
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
    private Double temperature; 

    @JsonProperty("diskType")
    private String diskType;

    @JsonProperty("gpuModel")
    private String gpuModel;

    @JsonProperty("reportTime")
    private LocalDateTime reportTime;

    // 🔥 Added New Fields for Enhanced Monitoring
    @JsonProperty("ipAddress")
    private String ipAddress;

    @JsonProperty("diskUsage")
    private Double diskUsage;

    @JsonProperty("osVersion")
    private String osVersion;

    @JsonProperty("aiSuggestion")
    private String aiSuggestion;

    // 🔥 Added for Health Analysis (Red line fix)
    @JsonProperty("healthScore")
    private Integer healthScore;

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

    // --- New Getters and Setters ---
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Double getDiskUsage() { return diskUsage; }
    public void setDiskUsage(Double diskUsage) { this.diskUsage = diskUsage; }

    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }

    public String getAiSuggestion() { return aiSuggestion; }
    public void setAiSuggestion(String aiSuggestion) { this.aiSuggestion = aiSuggestion; }

    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }
}