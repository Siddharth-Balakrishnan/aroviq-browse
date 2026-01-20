package com.aroviq.aroviqd.identityAndDiscovery.Dto;

public class IdentityDto {
    private String name;
    private String version;
    private long pid;
    private long startedAt;

    public IdentityDto(String name, String version, long pid, long startedAt) {
        this.name = name;
        this.version = version;
        this.pid = pid;
        this.startedAt = startedAt;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public long getPid() {
        return pid;
    }

    public long getStartedAt() {
        return startedAt;
    }
}
