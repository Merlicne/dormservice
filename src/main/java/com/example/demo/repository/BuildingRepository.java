package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    @Query("SELECT b FROM Buildings b WHERE b.deleted_at IS NULL")
    List<Building> findNotDeletedAll();

    @Query("SELECT b FROM Buildings b WHERE b.buildingID = ?1 AND b.deleted_at IS NULL")
    Optional<Building> findNotDeletedById(int id);

}
