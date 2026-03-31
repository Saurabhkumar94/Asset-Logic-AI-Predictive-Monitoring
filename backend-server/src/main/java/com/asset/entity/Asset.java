package com.asset.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "assets")
@Data // Agar Lombok kaam nahi kar raha, toh niche wale manual methods zaruri hain
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;
    private String status;
    private double healthScore;
    private String previousHash;
    private String currentHash;

    // --- MANUALLY ADDING GETTERS AND SETTERS (Safe Side) ---
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getHealthScore() { return healthScore; }
    public void setHealthScore(double healthScore) { this.healthScore = healthScore; }

    public String getPreviousHash() { return previousHash; }
    public void setPreviousHash(String previousHash) { this.previousHash = previousHash; }

    public String getCurrentHash() { return currentHash; }
    public void setCurrentHash(String currentHash) { this.currentHash = currentHash; }
}