package com.aroviq.aroviqd.systemMemory.Lifecycle;

import com.aroviq.aroviqd.identityAndDiscovery.Dto.CapabilitiesDto;
import com.aroviq.aroviqd.identityAndDiscovery.Dto.IdentityDto;
import com.aroviq.aroviqd.identityAndDiscovery.Service.IdentityService;
import com.aroviq.aroviqd.systemMemory.Service.MemoryService;
import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * MemoryLifecycle handles the promotion of system snapshots to memory on
 * startup.
 */
@Component
public class MemoryLifecycle {
    private static final Logger log = LoggerFactory.getLogger(MemoryLifecycle.class);

    private final MemoryService memoryService;
    private final IdentityService identityService;
    private final ObjectMapper objectMapper;

    public MemoryLifecycle(MemoryService memoryService,
            IdentityService identityService,
            ObjectMapper objectMapper) {
        this.memoryService = memoryService;
        this.identityService = identityService;
        this.objectMapper = objectMapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        log.info("Daemon startup event received. Writing initial system snapshots to memory.");

        try {
            // 1. Daemon Identity Snapshot
            IdentityDto identity = identityService.getIdentity();
            @SuppressWarnings("unchecked")
            Map<String, Object> identityPayload = objectMapper.convertValue(identity, Map.class);
            memoryService.writeEntry("daemon", "identity", identityPayload);

            // 2. Capability Snapshot
            CapabilitiesDto capabilities = new CapabilitiesDto();
            @SuppressWarnings("unchecked")
            Map<String, Object> capabilityPayload = objectMapper.convertValue(capabilities, Map.class);
            memoryService.writeEntry("daemon", "capability", capabilityPayload);

            log.info("Identity and capability snapshots promoted to memory.");
        } catch (Exception e) {
            log.error("Failed to promote initial snapshots to memory: {}", e.getMessage());
            // Safe failure: memory behavior is auxiliary to daemon core
        }
    }
}
