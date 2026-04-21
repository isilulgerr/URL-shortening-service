package com.isil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isil.model.Url;
import com.isil.service.UrlService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/shorten")
public class UrlController {

    @Autowired
    private UrlService urlService;

    // 1. URL Shortening (POST /shorten)
    @PostMapping
    public ResponseEntity<Url> shorten(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(urlService.shortenUrl(request.get("url")));
    }

    // 2. Get the stats (GET /shorten/{shortCode}/stats)
    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<Url> getStats(@PathVariable String shortCode) {
        return urlService.getStats(shortCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Direct (GET /shorten/{shortCode})
    @GetMapping("/{shortCode}")
    public ResponseEntity<Object> redirect(@PathVariable String shortCode) {
        return urlService.getOriginalUrl(shortCode)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(url.getOriginalUrl()))
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update an existing short URL (PUT /shorten/{shortCode})
    @PutMapping("/{shortCode}")
    public ResponseEntity<Url> update(@PathVariable String shortCode, @RequestBody Map<String, String> request) {
        String newUrl = request.get("url");
        return urlService.updateUrl(shortCode, newUrl)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete a short URL (DELETE /shorten/{shortCode})
    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> delete(@PathVariable String shortCode) {
        if (urlService.deleteUrl(shortCode)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}