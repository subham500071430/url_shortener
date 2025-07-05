package com.app.URLShortener.repository;

import com.app.URLShortener.entity.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapper, String> {

    Optional<UrlMapper> findByLongUrl(String longUrl);
}
