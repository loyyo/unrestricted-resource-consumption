package com.example.urc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/vulnerabilities")
@RequiredArgsConstructor
@Log4j2
public class VulnerableController {

    private String[] strings = new String[10];
    private int howmany = 0;


    @PostMapping(path = "/memory-leak-exploit")
    public ResponseEntity<String> memoryLeakExploit(@RequestBody String request) {
        strings[howmany++] = request;
        return ResponseEntity.ok().body("Data received! Current data: " + Arrays.toString(strings));
    }

    @PostMapping(path = "/dos-cpu-exploit")
    public ResponseEntity<String> dosCPU(@RequestBody String request) {
        while (request.equals("Malicious data")) {
            log.info("Processing request: {}", request);
        }
        return ResponseEntity.ok().body("Data received!");
    }

    @GetMapping(path = "/multi-request-exploit")
    public ResponseEntity<String> multiRequestExploit() {
        // Kosztowne obliczenia
        BigInteger result = BigInteger.ZERO;
        for (long i = 0; i < 100000000; i++) {
            result = result.add(BigInteger.valueOf(i));
        }
        return ResponseEntity.ok().body("Result: " + result);
    }

    @PostMapping(path = "/recursive-function-exploit")
    public ResponseEntity<String> recursiveFunctionExploit(@RequestBody String request) {
        int result = recursiveFunction(Integer.valueOf(request));
        return ResponseEntity.ok().body("Data received: " + result);
    }

    private int recursiveFunction(Integer value) {
        if (value > 0) {
            return recursiveFunction(value - 1);
        }
        return 0;
    }

    private final List<byte[]> fileList = new ArrayList<>();

    @PostMapping(path = "/upload-file")
    public ResponseEntity<String> uploadFiles(@RequestBody byte[] request) {
        fileList.add(request);
        return ResponseEntity.ok().body("File uploaded");
    }
}
