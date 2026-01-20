package com.aroviq.aroviqd.identityAndDiscovery.Service;

import com.aroviq.aroviqd.identityAndDiscovery.Dto.IdentityDto;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
public class IdentityService {

    public IdentityDto getIdentity() {
        return new IdentityDto(
                "aroviqd",
                "0.0.1-SNAPSHOT",
                ProcessHandle.current().pid(),
                ManagementFactory.getRuntimeMXBean().getStartTime());
    }
}
