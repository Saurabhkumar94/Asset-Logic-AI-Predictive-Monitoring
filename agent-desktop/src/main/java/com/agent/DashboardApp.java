package com.agent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow; 
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import oshi.SystemInfo;
import com.asset.model.SystemMetrics;
import com.asset.service.MetricsService;
import com.agent.client.MetricsClient;
import com.asset.util.InputMonitor;

public class DashboardApp extends Application {
    private XYChart.Series<Number, Number> cpuSeries = new XYChart.Series<>();
    private SystemInfo si = new SystemInfo();
    private int time = 0;
    
    private final ObservableList<SystemMetrics> metricsData = FXCollections.observableArrayList();
    private TableView<SystemMetrics> table = new TableView<>();

    private final MetricsService metricsService = new MetricsService();
    private final MetricsClient metricsClient = new MetricsClient();
    
    private Label systemInfoLabel = new Label("Detecting Device...");
    private Label statusLabel = new Label("System Status: Monitoring...");

    @Override
    public void start(Stage stage) {
        try {
            InputMonitor.startMonitoring();
        } catch (Exception e) {
            System.err.println("Monitoring error: " + e.getMessage());
        }

        stage.setTitle("Asset Logic AI - Live Monitoring & AI Predictor");

        String hostName = si.getOperatingSystem().getNetworkParams().getHostName();
        String ipAddress = "10.147.72.79"; 
        
        systemInfoLabel.setText("📍 Device: " + hostName + " | IP: " + ipAddress);
        systemInfoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-padding: 5; -fx-font-weight: bold;");

        // --- TABLE SETUP START ---
        TableColumn<SystemMetrics, String> deviceCol = new TableColumn<>("Device");
        deviceCol.setCellValueFactory(new PropertyValueFactory<>("deviceName"));

        TableColumn<SystemMetrics, String> ipCol = new TableColumn<>("IP Address");
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));

        // 🔥 Added Temperature Column
        TableColumn<SystemMetrics, Double> tempCol = new TableColumn<>("Temp °C");
        tempCol.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        TableColumn<SystemMetrics, Double> ramCol = new TableColumn<>("RAM %");
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ramUsage"));

        // 🔥 Added Disk Usage Column
        TableColumn<SystemMetrics, Double> diskCol = new TableColumn<>("Disk (GB)");
        diskCol.setCellValueFactory(new PropertyValueFactory<>("diskUsage"));

        // 🔥 Added OS Version Column
        TableColumn<SystemMetrics, String> osCol = new TableColumn<>("OS Version");
        osCol.setCellValueFactory(new PropertyValueFactory<>("osVersion"));

        TableColumn<SystemMetrics, Integer> healthCol = new TableColumn<>("Health %");
        healthCol.setCellValueFactory(new PropertyValueFactory<>("healthScore"));

        TableColumn<SystemMetrics, String> suggestCol = new TableColumn<>("AI Prediction & Suggestion");
        suggestCol.setCellValueFactory(new PropertyValueFactory<>("aiSuggestion"));

        // 🔥 Added new columns to the setAll list
        table.getColumns().setAll(deviceCol, ipCol, osCol, tempCol, ramCol, diskCol, healthCol, suggestCol);
        table.setItems(metricsData);
        table.setPrefHeight(300);

        table.setRowFactory(tv -> new TableRow<SystemMetrics>() {
            @Override
            protected void updateItem(SystemMetrics item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle(""); 
                } else {
                    if (item.getHealthScore() != null && item.getHealthScore() < 60) {
                        setStyle("-fx-background-color: #ff4d4d; -fx-text-background-color: white;"); 
                    } 
                    else {
                        setStyle("-fx-background-color: #007bff; -fx-text-background-color: white;");
                    }
                }
            }
        });
        // --- TABLE SETUP END ---

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis(0, 100, 10);
        xAxis.setLabel("Time (s)");
        yAxis.setLabel("Usage %");

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Real-time CPU Usage");
        cpuSeries.setName("CPU Load");
        lineChart.getData().add(cpuSeries);

        statusLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10;");

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    SystemMetrics metrics = metricsService.collectMetrics();
                    
                    Platform.runLater(() -> {
                        cpuSeries.getData().add(new XYChart.Data<>(time++, metrics.getCpuUsage()));
                        if (cpuSeries.getData().size() > 30) cpuSeries.getData().remove(0);
                        
                        metricsData.add(0, metrics); 
                        if (metricsData.size() > 50) metricsData.remove(50); 
                        
                        statusLabel.setText(metrics.getStatus() + " | Health: " + metrics.getHealthScore() + "%");
                    });

                    metricsClient.sendMetrics(metrics);
                    Thread.sleep(3000); 
                } catch (Exception e) { 
                    e.printStackTrace(); 
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();

        VBox layout = new VBox(systemInfoLabel, lineChart, table, statusLabel);
        // Window size thodi bada di hai taaki naye columns fit aa jayein
        stage.setScene(new Scene(layout, 1150, 750)); 
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}