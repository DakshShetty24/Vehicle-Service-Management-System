package com.vehicle.service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private double serviceCharge;

    private double partsCharge;

    private double totalAmount;

    private String paymentStatus;
}