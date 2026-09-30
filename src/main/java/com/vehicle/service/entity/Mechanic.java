package com.vehicle.service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String specialization;
}