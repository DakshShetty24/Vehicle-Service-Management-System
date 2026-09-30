package com.vehicle.service.controller;

import com.vehicle.service.repository.BillRepository;
import com.vehicle.service.service.BillService;
import com.vehicle.service.entity.User;
import com.vehicle.service.repository.BookingRepository;
import com.vehicle.service.repository.MechanicRepository;
import com.vehicle.service.repository.SparePartRepository;
import com.vehicle.service.repository.UserRepository;
import com.vehicle.service.repository.VehicleRepository;
import com.vehicle.service.service.BookingService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private MechanicRepository mechanicRepo;

    @Autowired
    private SparePartRepository sparePartRepo;

    @Autowired
    private BookingService bookingService;

    @Autowired
private BillService billService;

@Autowired
private BillRepository billRepo;


    // Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        model.addAttribute(
                "customers",
                userRepo.findByRoleAndNameContainingIgnoreCase(
                        "CUSTOMER",
                        ""
                )
        );

        model.addAttribute(
                "vehicles",
                vehicleRepo.findAll()
        );

        model.addAttribute(
                "bookings",
                bookingRepo.findAll()
        );

        model.addAttribute(
                "mechanics",
                mechanicRepo.findAll()
        );

        model.addAttribute(
                "spareParts",
                sparePartRepo.findAll()
        );
        model.addAttribute(
        "bills",
        billRepo.findAll()
);

        return "admin/dashboard";
    }


    // Assign mechanic
    @PostMapping("/admin/booking/assign")
    public String assignMechanic(
            @RequestParam Long bookingId,
            @RequestParam Long mechanicId,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        bookingService.assignMechanic(
                bookingId,
                mechanicId
        );

        return "redirect:/admin/dashboard";
    }


    // Update booking status
    @PostMapping("/admin/booking/status")
    public String updateStatus(
            @RequestParam Long bookingId,
            @RequestParam String status,
            @RequestParam(required = false) String remarks,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        bookingService.updateStatus(
                bookingId,
                status,
                remarks
        );

        return "redirect:/admin/dashboard";
    }
    // Show Add Mechanic page
@GetMapping("/admin/mechanics/add")
public String addMechanicPage(HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    return "admin/add-mechanic";
}


// Save Mechanic
@PostMapping("/admin/mechanics/save")
public String saveMechanic(
        @RequestParam String name,
        @RequestParam String phone,
        @RequestParam String specialization,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    com.vehicle.service.entity.Mechanic mechanic =
            new com.vehicle.service.entity.Mechanic();

    mechanic.setName(name);
    mechanic.setPhone(phone);
    mechanic.setSpecialization(specialization);

    mechanicRepo.save(mechanic);

    return "redirect:/admin/dashboard";
}
// Create bill for a booking
@PostMapping("/admin/bill/create")
public String createBill(
        @RequestParam Long bookingId,
        @RequestParam double serviceCharge,
        @RequestParam double partsCharge,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    billService.createBill(
            bookingId,
            serviceCharge,
            partsCharge
    );

    return "redirect:/admin/dashboard";
}
// Show Create Bill page
@GetMapping("/admin/bill/create")
public String createBillPage(
        HttpSession session,
        Model model) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    model.addAttribute(
            "bookings",
            bookingRepo.findAll()
                    .stream()
                    .filter(booking ->
                            "Completed".equals(booking.getStatus()))
                    .toList()
    );

    return "admin/create-bill";
}
// Show Add Spare Part page
@GetMapping("/admin/parts/add")
public String addPartPage(HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    return "admin/add-part";
}


// Save Spare Part
@PostMapping("/admin/parts/save")
public String savePart(
        @RequestParam String partName,
        @RequestParam double price,
        @RequestParam int quantity,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    com.vehicle.service.entity.SparePart part =
            new com.vehicle.service.entity.SparePart();

    part.setPartName(partName);
    part.setPrice(price);
    part.setQuantity(quantity);

    sparePartRepo.save(part);

    return "redirect:/admin/dashboard";
}
}