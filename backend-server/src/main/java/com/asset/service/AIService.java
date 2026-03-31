package com.asset.service;

import com.asset.model.SystemMetrics;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

@Service
public class AIService {
    private final String PYTHON_URL = "http://localhost:8000/predict";

    public SystemMetrics analyzeHealth(SystemMetrics metrics) {
        //  Step 1: Initial values (Default High Health)
        int score = 100;
        String prediction = "System Healthy & Stable";
        
        System.out.println(" AI Engine: Analyzing health for " + metrics.getDeviceName());

        try {
            // 1. CPU Load Analysis (Basic Scoring)
            double cpu = metrics.getCpuUsage();
            if (cpu > 90) {
                score -= 40;
                prediction = " CRITICAL: CPU Overload";
            } else if (cpu > 70) {
                score -= 20;
                prediction = " Warning: High System Stress";
            } else if (cpu > 40) {
                score -= 10;
            }

            // 2. Smart Thermal Analysis (Fix for 0.0°C)
            String status = metrics.getStatus();
            if (status != null && status.contains("Temp: ")) {
                try {
                    String tempVal = status.split("Temp: ")[1].split("°C")[0].trim();
                    double temp = Double.parseDouble(tempVal);

                    // Agar temp 0 se bada hai tabhi score minus hoga (Sensor Fix)
                    if (temp > 1.0) { 
                        if (temp > 80) {
                            score -= 30;
                            prediction = " ALERT: Hardware Overheating!";
                        } else if (temp > 70) {
                            score -= 10;
                            prediction = " Warning: High Temperature";
                        }
                    } else {
                        System.out.println("ℹ Sensor returned 0.0°C. Skipping thermal penalty.");
                    }
                } catch (Exception parseEx) {
                    System.out.println("ℹTemperature parsing error, using CPU metrics only.");
                }
            }
        } catch (Exception e) {
            System.err.println(" AI Analysis error: " + e.getMessage());
        }

        // Final score guarantee (At least 10 score)
        int finalScore = Math.max(10, score); 
        
        if (finalScore < 50 && !prediction.contains("ALERT")) {
            prediction = " MAINTENANCE REQUIRED";
        }

        //  Step 2: Set Health & Prediction
        metrics.setHealthScore(finalScore);
        metrics.setPrediction(prediction);

        //  Step 3: Blockchain Hashing (Security)
        String pHash = (metrics.getPreviousHash() != null) ? metrics.getPreviousHash() : "0";
        String dataToHash = metrics.getDeviceName() + metrics.getCpuUsage() + finalScore + pHash;
        metrics.setCurrentHash(generateSHA256(dataToHash));

        return metrics;
    }

    private String generateSHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            return "HASH_ERROR_" + System.currentTimeMillis();
        }
    }

    public String getPythonPrediction(Object hardwareData) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(PYTHON_URL, hardwareData, String.class);
        } catch (Exception e) {
            return "Local AI Engine Active";
        }
    }
}