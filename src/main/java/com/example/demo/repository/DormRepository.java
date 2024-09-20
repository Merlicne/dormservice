package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Dorm;

@Repository
public interface DormRepository extends JpaRepository<Dorm,UUID> {

    @Query(value = "SELECT * FROM Dorm_Info d WHERE d.deleted_at IS NULL", nativeQuery = true)
    List<Dorm> findNotDeletedAll();

    @Query(value = "SELECT * FROM Dorm_Info d WHERE d.dormID = ?1 AND d.deleted_at IS NULL", nativeQuery = true)
    Optional<Dorm> findNotDeletedById(UUID id);
}
