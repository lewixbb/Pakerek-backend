package com.pakerek.auth.user.repository;

import com.pakerek.auth.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByEmail(String email);
     Boolean existsByEmail(String email);
}
