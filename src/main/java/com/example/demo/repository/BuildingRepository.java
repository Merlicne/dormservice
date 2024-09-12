package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

}
