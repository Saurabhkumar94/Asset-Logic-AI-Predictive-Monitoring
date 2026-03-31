package com.asset.util;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import oshi.SystemInfo;
import oshi.hardware.UsbDevice;
import oshi.hardware.Sensors;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputMonitor implements NativeKeyListener, NativeMouseListener {
    private static boolean activityDetected = false;

    public static void startMonitoring() {
        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();
            InputMonitor monitor = new InputMonitor();
            GlobalScreen.addNativeKeyListener(monitor);
            GlobalScreen.addNativeMouseListener(monitor);
            System.out.println("⌨️ Stability & Thermal Monitoring Active...");
        } catch (Exception e) {
            System.err.println(" JNativeHook Error: " + e.getMessage());
        }
    }

    public static String getThermalStatus(SystemInfo si) {
        try {
            Sensors sensors = si.getHardware().getSensors();
            double temp = sensors.getCpuTemperature();
            // Backend AI ke liye format: "Temp: 45.5°C" [cite: 2026-02-18]
            return "Temp: " + String.format("%.1f", temp) + "°C";
        } catch (Exception e) { return "Temp: N/A"; }
    }

    public static String getStabilityStatus(SystemInfo si) {
        try {
            // FIXED: OperatingSystem se uptime fetch karna taaki error na aaye
            long uptimeSeconds = si.getOperatingSystem().getSystemUptime(); 
            long days = uptimeSeconds / 86400;
            long hours = (uptimeSeconds % 86400) / 3600;
            long minutes = (uptimeSeconds % 3600) / 60;
            return "Uptime: " + days + "d " + hours + "h " + minutes + "m";
        } catch (Exception e) { return "Uptime: N/A"; }
    }

    public static String getUsbStatus(SystemInfo si) {
        try {
            List<UsbDevice> usbDevices = si.getHardware().getUsbDevices(false);
            return "USBs: " + usbDevices.size();
        } catch (Exception e) { return "USBs: Error"; }
    }

    @Override public void nativeKeyPressed(NativeKeyEvent e) { activityDetected = true; }
    @Override public void nativeMousePressed(NativeMouseEvent e) { activityDetected = true; }

    public static String getPeripheralStatus() {
        if (activityDetected) {
            activityDetected = false;
            return "🟢 ACTIVE";
        } else { return "🔴 IDLE"; }
    }

    // Unused methods for interface implementation
    public void nativeKeyReleased(NativeKeyEvent e) {}
    public void nativeKeyTyped(NativeKeyEvent e) {}
    public void nativeMouseClicked(NativeMouseEvent e) {}
    public void nativeMouseReleased(NativeMouseEvent e) {}
    public void nativeMouseMoved(NativeMouseEvent e) {}
    public void nativeMouseDragged(NativeMouseEvent e) {}
}