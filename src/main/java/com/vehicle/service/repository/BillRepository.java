package com.vehicle.service.repository;

import com.vehicle.service.entity.Bill;
import com.vehicle.service.entity.Booking;
import com.vehicle.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByBooking(Booking booking);

    List<Bill> findByBookingVehicleOwner(User owner);
}