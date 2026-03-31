package com.asset;

import javafx.beans.property.*;

public class MetricData {
    private final StringProperty device = new SimpleStringProperty();
    private final StringProperty ipAddress = new SimpleStringProperty();
    private final StringProperty osVersion = new SimpleStringProperty();
    private final DoubleProperty temp = new SimpleDoubleProperty();
    private final DoubleProperty ram = new SimpleDoubleProperty();
    private final DoubleProperty disk = new SimpleDoubleProperty();
    private final IntegerProperty health = new SimpleIntegerProperty();
    
    // 🔥 Missing Properties for AI (Red Line Fix)
    private final StringProperty aiSuggestion = new SimpleStringProperty();
    private final StringProperty prediction = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    // 1. Khali Constructor
    public MetricData() {}

    // 2. 🔥 Constructor with Parameters (Line 145 ki Red Line isi se hategi)
    public MetricData(String deviceName, String statusValue, int healthScore, String predictionValue) {
        setDevice(deviceName);
        setStatus(statusValue);
        setHealth(healthScore);
        setPrediction(predictionValue);
    }

    // --- Getters and Setters for JavaFX TableView ---

    // Device
    public String getDevice() { return device.get(); }
    public void setDevice(String value) { device.set(value); }
    public StringProperty deviceProperty() { return device; }

    // IP Address
    public String getIpAddress() { return ipAddress.get(); }
    public void setIpAddress(String value) { ipAddress.set(value); }
    public StringProperty ipAddressProperty() { return ipAddress; }

    // OS Version
    public String getOsVersion() { return osVersion.get(); }
    public void setOsVersion(String value) { osVersion.set(value); }
    public StringProperty osVersionProperty() { return osVersion; }

    // Temperature
    public double getTemp() { return temp.get(); }
    public void setTemp(double value) { temp.set(value); }
    public DoubleProperty tempProperty() { return temp; }

    // RAM Usage
    public double getRam() { return ram.get(); }
    public void setRam(double value) { ram.set(value); }
    public DoubleProperty ramProperty() { return ram; }

    // Disk Usage
    public double getDisk() { return disk.get(); }
    public void setDisk(double value) { disk.set(value); }
    public DoubleProperty diskProperty() { return disk; }

    // Health Score
    public int getHealth() { return health.get(); }
    public void setHealth(int value) { health.set(value); }
    public IntegerProperty healthProperty() { return health; }

    // 🔥 Added Setters/Getters for missing fields (Line 153-156 ki Red Line fix karne ke liye)
    
    public String getAiSuggestion() { return aiSuggestion.get(); }
    public void setAiSuggestion(String value) { aiSuggestion.set(value); }
    public StringProperty aiSuggestionProperty() { return aiSuggestion; }

    public String getPrediction() { return prediction.get(); }
    public void setPrediction(String value) { prediction.set(value); }
    public StringProperty predictionProperty() { return prediction; }

    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }
    public StringProperty statusProperty() { return status; }
}