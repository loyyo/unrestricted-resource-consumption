package com.example.urc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/secure")
@RequiredArgsConstructor
@Log4j2
public class SecureController {

    private static final List<String> staticList = new ArrayList<>();
    private BigInteger bigInteger = BigInteger.ZERO;
    private long lastUpdate = 0;

    @PostMapping(path = "/memory-leak-exploit")
    public ResponseEntity<String> memoryLeakExploit(@RequestBody String request) {
        staticList.add(request);
        if (staticList.size() > 10) {
            staticList.remove(0);
        }
        return ResponseEntity.ok().body("Data received! Current data: " + staticList);
    }

    @PostMapping(path = "/dos-cpu-exploit")
    public ResponseEntity<String> dosCPU(@RequestBody String request) {
        long start = System.currentTimeMillis();
        long end = start + 3 * 1000;
        while (request.equals("Malicious data")) {
            log.info("Processing request: {}", request);
            if (System.currentTimeMillis() > end) {
                return ResponseEntity.internalServerError().body("Data lost!");
            }
        }
        log.info("Processing request: {}", request);
        return ResponseEntity.ok().body("Data received!");
    }

    @GetMapping(path = "/multi-request-exploit")
    public ResponseEntity<String> multiRequestExploit() {
        // Kosztowne obliczenia
        if (lastUpdate < System.currentTimeMillis() - (10 * 1000)) {
            bigInteger = BigInteger.ZERO;
            for (long i = 0; i < 100000000; i++) {
                bigInteger = bigInteger.add(BigInteger.valueOf(i));
            }
            lastUpdate = System.currentTimeMillis();
        }
        return ResponseEntity.ok().body("Result: " + bigInteger);
    }

    @PostMapping(path = "/recursive-function-exploit")
    public ResponseEntity<String> recursiveFunctionExploit(@RequestBody String request) {
        int result = recursiveFunction(Integer.valueOf(request), 0);
        return ResponseEntity.ok().body("Data received: " + result);
    }

    private int recursiveFunction(Integer value, Integer depth) {
        if (depth > 100 || value <= 0) {
            return 0;
        }
        return recursiveFunction(value - 1, depth + 1);
    }

    private final List<byte[]> fileList = new ArrayList<>();

    @PostMapping(path = "/upload-file")
    public ResponseEntity<String> uploadFiles(@RequestBody byte[] request) {
        int maxSizeInBytes = 1024 * 1024;
        if (request.length > maxSizeInBytes) {
            return ResponseEntity.badRequest().body("Your file is too big");
        }
        fileList.add(request);
        return ResponseEntity.ok().body("File uploaded");
    }
}
