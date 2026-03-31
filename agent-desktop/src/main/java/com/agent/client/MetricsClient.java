package com.agent.client;

import com.asset.model.SystemMetrics;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class MetricsClient {

    private final RestTemplate restTemplate = new RestTemplate();
    
    // 🔥 ZAROORI: Backend URL check karein
    private final String BACKEND_URL = "http://localhost:8080/api/metrics/save";

    public void sendMetrics(SystemMetrics metrics) {
        try {
            // 1. JSON Headers set karna (Taaki NULL na aaye)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. Data aur Headers ko ek Packet mein daalna
            HttpEntity<SystemMetrics> request = new HttpEntity<>(metrics, headers);

            System.out.println("🚀 [AGENT] Attempting to send: Temp=" + metrics.getTemperature() + 
                               " | RAM=" + metrics.getRamUsage() + " | Disk=" + metrics.getDiskType());
            
            // 3. Post Request bhejna
            ResponseEntity<String> response = restTemplate.postForEntity(BACKEND_URL, request, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("✅ [AGENT] Data Delivered Successfully to Backend!");
            }
        } catch (Exception e) {
            System.err.println("❌ [AGENT] Connection Error: " + e.getMessage());
        }
    }
}