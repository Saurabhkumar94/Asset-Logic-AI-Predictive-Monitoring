package com.asset.repository;

import com.asset.model.SystemMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MetricsRepository extends JpaRepository<SystemMetrics, Long> {
    
    // Error fix: Database se sabse naya (latest) record nikalne ke liye
    // Iske bina AssetController mein 'findTopByOrderByIdDesc' error dega
    Optional<SystemMetrics> findTopByOrderByIdDesc();
}