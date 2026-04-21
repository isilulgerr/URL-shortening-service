package com.isil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isil.model.Url;
import com.isil.repository.UrlRepository;

import java.util.Random;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    public Url shortenUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(generateShortCode());
        return urlRepository.save(url);
    }

    public Optional<Url> getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode).map(url -> {
            url.setAccessCount(url.getAccessCount() + 1);
            return urlRepository.save(url);
        });
    }

    public Optional<Url> getStats(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

    public boolean deleteUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode).map(url -> {
            urlRepository.delete(url);
            return true;
        }).orElse(false);
    }

    public Optional<Url> updateUrl(String shortCode, String newUrl) {
        return urlRepository.findByShortCode(shortCode).map(url -> {
            url.setOriginalUrl(newUrl);
            url.setUpdatedAt(java.time.LocalDateTime.now());
            return urlRepository.save(url);
        });
    }
}