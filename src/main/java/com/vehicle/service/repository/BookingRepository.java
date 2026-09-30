package com.vehicle.service.repository;

import com.vehicle.service.entity.Booking;
import com.vehicle.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByVehicleOwner(User owner);

    long countByStatus(String status);
}