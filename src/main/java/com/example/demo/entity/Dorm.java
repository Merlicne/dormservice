package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Table(name = "Dorm_Info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE Dorm_Info SET deleted_at = current_timestamp WHERE dormID = ?")
public class Dorm {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(name = "dormID")
    private UUID dormID;

    @Column(name = "dormName")
    private String name ;

    @Column(name = "telephone")
    private String telephone ;


    @Column(name = "address")
    private String address;

    @Column(name = "creater")
    private String creater;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    
    
}
