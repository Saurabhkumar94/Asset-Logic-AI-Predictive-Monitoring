package com.asset.util;

public class FormatUtil {
    public static String bytesToGB(long bytes) {
        return (bytes / (1024*1024*1024)) + " GB";
    }
} 