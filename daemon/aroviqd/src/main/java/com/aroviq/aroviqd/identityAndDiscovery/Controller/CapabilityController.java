package com.aroviq.aroviqd.identityAndDiscovery.Controller;

import com.aroviq.aroviqd.identityAndDiscovery.Dto.CapabilitiesDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CapabilityController {

    @GetMapping("/capabilities")
    public CapabilitiesDto getCapabilities() {
        return new CapabilitiesDto();
    }
}
