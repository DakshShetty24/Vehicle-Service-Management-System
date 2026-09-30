package com.vehicle.service.service;

import com.vehicle.service.entity.Booking;
import com.vehicle.service.entity.Mechanic;
import com.vehicle.service.entity.Vehicle;
import com.vehicle.service.repository.BookingRepository;
import com.vehicle.service.repository.MechanicRepository;
import com.vehicle.service.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private MechanicRepository mechanicRepo;


    // Create a new service booking
    public void book(
            Long vehicleId,
            String serviceType,
            LocalDate appointmentDate) {

        Vehicle vehicle = vehicleRepo
                .findById(vehicleId)
                .orElseThrow();

        Booking booking = new Booking();

        booking.setVehicle(vehicle);
        booking.setServiceType(serviceType);
        booking.setBookingDate(LocalDate.now());
        booking.setAppointmentDate(appointmentDate);
        booking.setStatus("Pending");

        bookingRepo.save(booking);
    }


    // Assign mechanic to booking
    public void assignMechanic(
            Long bookingId,
            Long mechanicId) {

        Booking booking = bookingRepo
                .findById(bookingId)
                .orElseThrow();

        Mechanic mechanic = mechanicRepo
                .findById(mechanicId)
                .orElseThrow();

        booking.setMechanic(mechanic);

        if ("Pending".equals(booking.getStatus())) {
            booking.setStatus("Confirmed");
        }

        bookingRepo.save(booking);
    }


    // Update booking status
    public void updateStatus(
            Long bookingId,
            String status,
            String remarks) {

        Booking booking = bookingRepo
                .findById(bookingId)
                .orElseThrow();

        booking.setStatus(status);
        booking.setRemarks(remarks);

        bookingRepo.save(booking);
    }
}