package com.aroviq.aroviqd.identityAndDiscovery.Lifecycle;

import tools.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DiscoveryLifecycle {

    @Value("${server.port:8080}")
    private int httpPort;

    private static final String DAEMON_DIR = System.getProperty("user.home") + "/.aroviq";
    private static final String DAEMON_JSON = DAEMON_DIR + "/daemon.json";

    private final ObjectMapper objectMapper;

    public DiscoveryLifecycle(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        try {
            File dir = new File(DAEMON_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Map<String, Object> discoveryData = new HashMap<>();
            discoveryData.put("pid", ProcessHandle.current().pid());
            discoveryData.put("http_port", httpPort);
            discoveryData.put("websocket_port", httpPort);
            discoveryData.put("version", "0.0.1-SNAPSHOT");
            discoveryData.put("started_at",
                    Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));

            objectMapper.writeValue(new File(DAEMON_JSON), discoveryData);
            System.out.println("Daemon discovery file written to: " + DAEMON_JSON);

        } catch (Exception e) {
            System.err.println("Failed to write discovery file: " + e.getMessage());
        }
    }

    @PreDestroy
    public void onShutdown() {
        File file = new File(DAEMON_JSON);
        if (file.exists()) {
            file.delete();
            System.out.println("Daemon discovery file removed.");
        }
    }
}
