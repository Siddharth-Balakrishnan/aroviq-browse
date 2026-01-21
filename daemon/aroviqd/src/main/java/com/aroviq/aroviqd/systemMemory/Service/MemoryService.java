package com.aroviq.aroviqd.systemMemory.Service;

import com.aroviq.aroviqd.systemMemory.Model.MemoryEntry;
import tools.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

/**
 * MemoryService is responsible for managing the append-only system memory
 * ledger.
 * It provides write-only access to the structured truth ledger (NDJSON).
 */
@Service
public class MemoryService {
    private static final Logger log = LoggerFactory.getLogger(MemoryService.class);
    private static final String MEMORY_DIR = System.getProperty("user.home") + "/.aroviq/memory";
    private static final String LEDGER_FILE = MEMORY_DIR + "/ledger.ndjson";

    private final ObjectMapper objectMapper;
    private BufferedWriter writer;
    private FileOutputStream fos;

    public MemoryService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        initializeLedger();
    }

    private synchronized void initializeLedger() {
        try {
            Path path = Paths.get(MEMORY_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            File file = new File(LEDGER_FILE);
            // Append mode: true
            this.fos = new FileOutputStream(file, true);
            this.writer = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));

            log.info("Append-only system memory ledger initialized at: {}", LEDGER_FILE);
        } catch (IOException e) {
            log.error("CRITICAL: Failed to initialize memory ledger: {}", e.getMessage());
        }
    }

    /**
     * Appends a structured entry to the system memory ledger.
     * Conform exactly to schema version 1.
     * 
     * @param source   Must be one of: daemon | user | agent
     * @param category Must be one of: identity | capability | decision | constraint
     *                 | observation
     * @param payload  Structured JSON payload (no free text blobs)
     */
    public synchronized void writeEntry(String source, String category, Map<String, Object> payload) {
        if (writer == null) {
            log.error("Cannot write to memory: writer is null");
            return;
        }

        MemoryEntry entry = new MemoryEntry(
                UUID.randomUUID().toString(),
                Instant.now().atOffset(ZoneOffset.UTC).toInstant().toString(), // ISO-8601
                source,
                category,
                1, // schema_version is mandatory
                payload);

        try {
            String jsonEntry = objectMapper.writeValueAsString(entry);
            writer.write(jsonEntry);
            writer.newLine();
            writer.flush(); // REQUIRED: Flush after every write

            // Physical durability for auditability
            fos.getFD().sync();

        } catch (IOException e) {
            log.error("Failed to append memory entry: {}", e.getMessage());
            // Safe failure: do not crash daemon for memory write failure
        }
    }

    @PreDestroy
    public synchronized void shutdown() {
        if (writer != null) {
            try {
                writer.flush();
                fos.getFD().sync();
                writer.close();
                log.info("System memory ledger closed gracefully.");
            } catch (IOException e) {
                log.error("Error during memory ledger shutdown: {}", e.getMessage());
            } finally {
                writer = null;
                fos = null;
            }
        }
    }
}
