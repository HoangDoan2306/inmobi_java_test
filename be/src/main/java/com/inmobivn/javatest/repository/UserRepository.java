package com.inmobivn.javatest.repository;

import com.inmobivn.javatest.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByScrId(String scrId);

    boolean existsByScrId(String scrId);

    void deleteByScrId(String scrId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.scrId = :scrId")
    Optional<User> findByScrIdForUpdate(@Param("scrId") String scrId);

    List<User> findTop10ByOrderByScoreDescScrIdAsc(Pageable pageable);
}