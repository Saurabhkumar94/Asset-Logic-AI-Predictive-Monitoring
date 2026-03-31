module com.asset {
    // 1. Core Modules
    requires java.net.http;
    requires java.logging;

    // 2. JavaFX Modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; 

    // 3. External Libraries
    requires com.github.oshi;
    requires jnativehook;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    // 4. Spring Modules (For MetricsClient)
    requires spring.web;
    requires spring.beans;
    requires spring.core;

    // 5. Opening packages for Reflection (Jackson & JavaFX)
    opens com.agent to javafx.graphics, javafx.fxml, spring.core; 
    opens com.asset.model to com.fasterxml.jackson.databind, spring.core;
    
    // 6. Exports
    exports com.agent;
    exports com.agent.client;
    exports com.asset.model;
    exports com.asset.service; // 🔥 Ye line ab error nahi degi
}