package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DormModel {

    @JsonAlias("dormID")
    private UUID dormID;

    @JsonAlias("name")
    private String name ;

    @JsonAlias("telephone")
    private String telephone ;

    @JsonAlias("address")
    private String address;

    @JsonAlias("creater")
    private String creater;

    @JsonAlias("createdAt")
    private LocalDateTime createdAt;


}
