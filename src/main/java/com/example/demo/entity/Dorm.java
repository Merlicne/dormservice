package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Dorm_Info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Dorm {
    @Column(name = "dormName")
    private String name ;

    @Column(name = "telephone")
    private String telephone ;

    @Column(name = "waterPrice")
    private double waterPrice;

    @Column(name = "elecPrice")
    private double elecPrice;

    @Column(name = "address")
    private String address;
    
}
