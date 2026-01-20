package com.aroviq.aroviqd.identityAndDiscovery.Dto;

public class CapabilitiesDto {
    private MemoryCapabilities memory = new MemoryCapabilities();
    private ExecutionCapabilities execution = new ExecutionCapabilities();
    private AiCapabilities ai = new AiCapabilities();

    public static class MemoryCapabilities {
        private boolean appendOnly = true;

        public boolean isAppendOnly() {
            return appendOnly;
        }
    }

    public static class ExecutionCapabilities {
        private boolean enabled = false;

        public boolean isEnabled() {
            return enabled;
        }
    }

    public static class AiCapabilities {
        private boolean enabled = false;

        public boolean isEnabled() {
            return enabled;
        }
    }

    public MemoryCapabilities getMemory() {
        return memory;
    }

    public ExecutionCapabilities getExecution() {
        return execution;
    }

    public AiCapabilities getAi() {
        return ai;
    }
}
