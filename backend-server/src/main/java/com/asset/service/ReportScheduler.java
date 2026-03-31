package com.asset.service;

import com.asset.repository.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ReportScheduler {

    @Autowired
    private MetricsRepository repository;

    /**
     * Har 5 minute (300,000 ms) mein database se metrics delete karega.
     * Isse Workbench overflow nahi hoga aur data clean rahega.
     */
    @Scheduled(fixedRate = 300000)
    public void autoCleanDatabase() {
        try {
            long count = repository.count();
            if (count > 0) {
                repository.deleteAll();
                System.out.println("🗑️ Workbench Auto-Cleaned: " + count + " rows deleted successfully.");
            } else {
                System.out.println("ℹ️ No data to clean at the moment.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error while cleaning database: " + e.getMessage());
        }
    }
}