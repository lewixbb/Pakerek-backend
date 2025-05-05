package com.pakerek.auth.user.repository;

import com.pakerek.auth.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     @Query("SELECT u FROM User u WHERE u.email LIKE %:filter%")
     Page<User> findAllByEmailWithPagingAndSorting(PageRequest pageRequest, String filter);
     Optional<User> findByEmail(String email);
     Boolean existsByEmail(String email);
}
