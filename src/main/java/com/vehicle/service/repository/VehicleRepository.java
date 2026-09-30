package com.vehicle.service.repository;

import com.vehicle.service.entity.User;
import com.vehicle.service.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByOwner(User owner);

    List<Vehicle> findByVehicleNumberContainingIgnoreCase(String number);
}