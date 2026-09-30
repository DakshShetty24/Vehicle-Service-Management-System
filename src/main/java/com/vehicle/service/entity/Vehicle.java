package com.vehicle.service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String vehicleNumber;

    private String vehicleModel;

    private String vehicleType;

    private int purchaseYear;
}