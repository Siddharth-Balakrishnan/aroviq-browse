package com.aroviq.aroviqd.requestResponseJournaling.Service;

import tools.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Map;

@Service
public class JournalService {

    private static final String LOG_DIR = System.getProperty("user.home") + "/.aroviq/logs";
    private final ObjectMapper objectMapper;
    private BufferedWriter writer;
    private String logFilePath;

    public JournalService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws IOException {
        Path logDirPath = Paths.get(LOG_DIR);
        if (!Files.exists(logDirPath)) {
            Files.createDirectories(logDirPath);
        }

        long pid = ProcessHandle.current().pid();
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String filename = String.format("aroviqd-%s-%d.log", timestamp, pid);
        File logFile = new File(LOG_DIR, filename);
        this.logFilePath = logFile.getAbsolutePath();

        this.writer = new BufferedWriter(new FileWriter(logFile, true));
        System.out.println("Journal log initialized at: " + logFilePath);
    }

    public synchronized void writeEntry(Map<String, Object> entry) {
        if (writer == null)
            return;
        try {
            String json = objectMapper.writeValueAsString(entry);
            writer.write(json);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write journal entry: " + e.getMessage());
        }
    }

    @PreDestroy
    public void close() {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
                System.out.println("Journal log closed: " + logFilePath);
            } catch (IOException e) {
                System.err.println("Error closing journal log: " + e.getMessage());
            }
        }
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}
