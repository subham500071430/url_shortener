package com.app.user.repository;

import com.app.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

       @Query("select token from RefreshToken where token =:refreshToken")
       Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
