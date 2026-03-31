package com.asset.repository;

import com.asset.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    // Ye method pichle (latest) record ko fetch karega taaki hum hash chain bana sakein.
    // Ye aapke 29 problems mein se "Data Tampering" ko rokne ke liye zaruri hai.
    Optional<Asset> findTopByOrderByIdDesc();
}

