package com.aroviq.aroviqd.requestResponseJournaling.Filter;

import com.aroviq.aroviqd.requestResponseJournaling.Service.JournalService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RequestResponseJournalFilter extends OncePerRequestFilter {

    private static final int MAX_BODY_SIZE = 10000 * 1024; // 10000 KB

    private final JournalService journalService;

    public RequestResponseJournalFilter(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, MAX_BODY_SIZE);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        Instant start = Instant.now();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            Instant end = Instant.now();
            captureAndJournal(wrappedRequest, wrappedResponse, start, end);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void captureAndJournal(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
            Instant start, Instant end) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("timestamp_start", start.toString());
        logEntry.put("timestamp_end", end.toString());
        logEntry.put("duration_ms", end.toEpochMilli() - start.toEpochMilli());
        logEntry.put("method", request.getMethod());
        logEntry.put("path", request.getRequestURI());
        logEntry.put("status_code", response.getStatus());

        // Headers
        Map<String, String> headers = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, request::getHeader));
        logEntry.put("headers", headers);

        // Request Body
        byte[] requestBody = request.getContentAsByteArray();
        logEntry.put("request_body", processBody(requestBody, "request"));

        // Response Body
        byte[] responseBody = response.getContentAsByteArray();
        logEntry.put("response_body", processBody(responseBody, "response"));

        journalService.writeEntry(logEntry);
    }

    private Object processBody(byte[] body, String type) {
        if (body == null || body.length == 0) {
            return null;
        }

        Map<String, Object> bodyInfo = new HashMap<>();
        boolean truncated = body.length > MAX_BODY_SIZE;
        int length = truncated ? MAX_BODY_SIZE : body.length;

        bodyInfo.put("content", new String(body, 0, length, StandardCharsets.UTF_8));
        if (truncated) {
            bodyInfo.put("truncated", true);
        }

        return bodyInfo;
    }
}
