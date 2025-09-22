package com.app.user.repository;

import com.app.user.entity.PublicKeyStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicKeyRepository extends JpaRepository<PublicKeyStore, Integer> {
}
