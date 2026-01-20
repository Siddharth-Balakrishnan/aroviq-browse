package com.aroviq.aroviqd.identityAndDiscovery.Controller;

import com.aroviq.aroviqd.identityAndDiscovery.Dto.IdentityDto;
import com.aroviq.aroviqd.identityAndDiscovery.Service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IdentityController {

    private final IdentityService identityService;

    @Autowired
    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping("/identity")
    public IdentityDto getIdentity() {
        return identityService.getIdentity();
    }
}
