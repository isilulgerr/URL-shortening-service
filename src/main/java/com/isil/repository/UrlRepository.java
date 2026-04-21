package com.isil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isil.model.Url;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
}