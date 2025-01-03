package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.*;


@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query(value = "SELECT * FROM Admin a WHERE a.username = ?1 AND a.deleted_at IS NULL", nativeQuery = true)
    Optional<Admin> findByUsername(String username);

    @Query(value = "SELECT * FROM Admin a WHERE a.deleted_at IS NULL", nativeQuery = true)
    List<Admin> findNotDeletedAll();

    @Query(value = "SELECT * FROM Admin a WHERE a.username = ?1 AND a.deleted_at IS NULL", nativeQuery = true)
    Optional<Admin> findNotDeletedByUsername(String username);


}
