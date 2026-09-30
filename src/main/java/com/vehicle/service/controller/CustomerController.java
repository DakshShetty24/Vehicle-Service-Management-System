package com.vehicle.service.controller;

import com.vehicle.service.entity.Bill;
import com.vehicle.service.service.BillService;
import com.vehicle.service.entity.User;
import com.vehicle.service.entity.Vehicle;
import com.vehicle.service.repository.BillRepository;
import com.vehicle.service.repository.BookingRepository;
import com.vehicle.service.repository.VehicleRepository;
import com.vehicle.service.service.BookingService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class CustomerController {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private BillRepository billRepo;

    @Autowired
    private BookingService bookingService;

    @Autowired
private BillService billService;


    @GetMapping("/customer/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"CUSTOMER".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        model.addAttribute(
                "vehicles",
                vehicleRepo.findByOwner(user)
        );

        model.addAttribute(
                "bookings",
                bookingRepo.findByVehicleOwner(user)
        );

        model.addAttribute(
                "bills",
                billRepo.findByBookingVehicleOwner(user)
        );

        return "customer/dashboard";
    }


    @GetMapping("/customer/vehicles/add")
    public String addVehicle(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"CUSTOMER".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("vehicle", new Vehicle());

        return "customer/add-vehicle";
    }


    @PostMapping("/customer/vehicles/save")
    public String saveVehicle(
            @ModelAttribute Vehicle vehicle,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"CUSTOMER".equals(user.getRole())) {
            return "redirect:/login";
        }

        vehicle.setOwner(user);

        vehicleRepo.save(vehicle);

        return "redirect:/customer/dashboard";
    }


    @GetMapping("/customer/book")
    public String bookPage(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"CUSTOMER".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute(
                "vehicles",
                vehicleRepo.findByOwner(user)
        );

        return "customer/book";
    }


    @PostMapping("/customer/book")
    public String bookService(
            @RequestParam Long vehicleId,
            @RequestParam String serviceType,
            @RequestParam LocalDate appointmentDate,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"CUSTOMER".equals(user.getRole())) {
            return "redirect:/login";
        }

        bookingService.book(
                vehicleId,
                serviceType,
                appointmentDate
        );

        return "redirect:/customer/dashboard";
    }
    // Show Payment Page
@GetMapping("/customer/pay")
public String paymentPage(
        @RequestParam Long billId,
        HttpSession session,
        Model model) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"CUSTOMER".equals(user.getRole())) {
        return "redirect:/login";
    }

    Bill bill = billService.getBillById(billId);

    if (bill == null) {
        return "redirect:/customer/dashboard";
    }

    // Make sure the bill belongs to the logged-in customer
    if (!bill.getBooking().getVehicle().getOwner().getId()
            .equals(user.getId())) {

        return "redirect:/customer/dashboard";
    }

    model.addAttribute("bill", bill);

    return "customer/payment";
}


// Process Payment
@PostMapping("/customer/pay")
public String processPayment(
        @RequestParam Long billId,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"CUSTOMER".equals(user.getRole())) {
        return "redirect:/login";
    }

    Bill bill = billService.getBillById(billId);

    if (bill == null) {
        return "redirect:/customer/dashboard";
    }

    // Make sure the bill belongs to the logged-in customer
    if (!bill.getBooking().getVehicle().getOwner().getId()
            .equals(user.getId())) {

        return "redirect:/customer/dashboard";
    }

    billService.markAsPaid(billId);

    return "redirect:/customer/dashboard";
}
}