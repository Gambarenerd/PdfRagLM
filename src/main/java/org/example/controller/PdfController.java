package org.example.controller;

import org.example.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    private final PdfService service;

    public PdfController(PdfService service) {
        this.service = service;
    }

    @GetMapping("/pdf/ask")
    public ResponseEntity<String> getAnswer(@RequestParam String query) {
        String answer = service.getAnswer(query);
        return ResponseEntity.ok(answer);
    }
}

