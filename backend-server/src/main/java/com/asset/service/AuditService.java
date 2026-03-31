package com.asset.service;

import com.asset.util.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    // Naye data ko pichle hash ke saath jodh kar naya hash banana [cite: 29, 156]
    public String generateCurrentHash(String data, String previousHash) {
        return SecurityUtil.calculateHash(data + previousHash);
    }
}