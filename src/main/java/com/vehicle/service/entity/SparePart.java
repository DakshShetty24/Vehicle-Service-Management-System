package com.vehicle.service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partName;

    private double price;

    private int quantity;
}