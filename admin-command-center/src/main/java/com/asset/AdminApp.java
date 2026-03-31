package com.asset;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.http.*;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminApp extends Application {
    private TableView<MetricData> table = new TableView<>();
    private String adminToken = ""; 

    @Override
    public void start(Stage stage) {
        // Step 1: Login to get JWT Token
        adminLogin("Admin-Saurabh");

        Label header = new Label("🛡️ Asset Logic AI: Smart Command Center");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        HBox topBar = new HBox(header);
        topBar.setStyle("-fx-background-color: #1e293b; -fx-padding: 15;");

        // --- Phase 35: Updated Columns with AI Data ---
        TableColumn<MetricData, String> deviceCol = new TableColumn<>("Device Name");
        deviceCol.setCellValueFactory(new PropertyValueFactory<>("device")); // Changed to match your MetricData model
        deviceCol.setPrefWidth(120);

        // 🚀 IP Address Column
        TableColumn<MetricData, String> ipCol = new TableColumn<>("IP Address");
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        ipCol.setPrefWidth(120);

        // 🚀 OS Version Column
        TableColumn<MetricData, String> osCol = new TableColumn<>("OS Version");
        osCol.setCellValueFactory(new PropertyValueFactory<>("osVersion"));
        osCol.setPrefWidth(120);

        // Metrics Column
        TableColumn<MetricData, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        // 🚀 Disk Usage Column
        TableColumn<MetricData, Double> diskCol = new TableColumn<>("Disk (GB)");
        diskCol.setCellValueFactory(new PropertyValueFactory<>("disk")); // Matches 'disk' in your MetricData
        diskCol.setPrefWidth(80);

        // Health Score Column
        TableColumn<MetricData, Integer> healthCol = new TableColumn<>("Health %");
        healthCol.setCellValueFactory(new PropertyValueFactory<>("health")); // Matches 'health' in your MetricData
        healthCol.setPrefWidth(80);

        // AI Suggestion Column (Added for full monitoring)
        TableColumn<MetricData, String> aiCol = new TableColumn<>("AI Advice");
        aiCol.setCellValueFactory(new PropertyValueFactory<>("aiSuggestion"));
        aiCol.setPrefWidth(200);

        // 🔥 Added all columns to table
        table.getColumns().setAll(deviceCol, ipCol, osCol, statusCol, diskCol, healthCol, aiCol);

        // 🚨 RED-BLUE ROW LOGIC
        table.setRowFactory(tv -> new TableRow<MetricData>() {
            @Override
            protected void updateItem(MetricData item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getHealth() < 60) {
                        setStyle("-fx-background-color: #ff4d4d; -fx-text-background-color: white;"); // RED
                    } else {
                        setStyle("-fx-background-color: #007bff; -fx-text-background-color: white;"); // BLUE
                    }
                }
            }
        });

        // Auto-refresh logic
        Thread refreshThread = new Thread(() -> {
            while (true) {
                fetchSecureMetrics();
                try { Thread.sleep(5000); } catch (Exception e) {}
            }
        });
        refreshThread.setDaemon(true);
        refreshThread.start();

        VBox layout = new VBox(table);
        VBox.setVgrow(table, Priority.ALWAYS);
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(layout);

        stage.setScene(new Scene(root, 1150, 650)); 
        stage.setTitle("Asset Logic AI - Final Secure Dashboard");
        stage.show();
    }

    private void adminLogin(String username) {
        try {
            URL url = new URL("http://localhost:8080/api/auth/login?username=" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                adminToken = in.readLine();
                in.close();
                System.out.println("🔐 Admin Logged In.");
            }
        } catch (Exception e) { System.err.println("❌ Auth Failed"); }
    }

    private void fetchSecureMetrics() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/metrics/all"))
                .header("Authorization", "Bearer " + adminToken)
                .GET().build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateTableContent);
        } catch (Exception e) {}
    }

    private void updateTableContent(String jsonData) {
        if (jsonData == null || jsonData.isEmpty()) return;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            javafx.collections.ObservableList<MetricData> dataList = javafx.collections.FXCollections.observableArrayList();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                
                // 🚀 Mapping data from JSON to MetricData Model
                MetricData data = new MetricData(
                    obj.optString("deviceName", "Unknown"),
                    obj.optString("status", "STABLE"),
                    obj.optInt("healthScore", 100),
                    obj.optString("prediction", "Stable")
                );

                data.setIpAddress(obj.optString("ipAddress", "N/A"));
                data.setOsVersion(obj.optString("osVersion", "N/A"));
                data.setDisk(obj.optDouble("diskUsage", 0.0));
                data.setAiSuggestion(obj.optString("aiSuggestion", "N/A"));
                data.setRam(obj.optDouble("ramUsage", 0.0));
                data.setTemp(obj.optDouble("temperature", 0.0));

                dataList.add(data);
            }
            Platform.runLater(() -> table.setItems(dataList));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) { launch(args); }
}