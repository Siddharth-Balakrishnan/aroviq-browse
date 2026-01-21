package com.aroviq.aroviqd.systemMemory.Model;

import java.util.Map;

/**
 * Represents a single entry in the append-only system memory ledger.
 * Conform exactly to the locked schema version 1.
 */
public record MemoryEntry(
    String id,
    String timestamp,
    String source,
    String category,
    int schema_version,
    Map<String, Object> payload
) {
}
