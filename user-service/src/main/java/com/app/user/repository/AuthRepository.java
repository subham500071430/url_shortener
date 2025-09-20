package com.app.user.repository;

import com.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,String> {
}
