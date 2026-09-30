
package com.vehicle.service.service;

import com.vehicle.service.entity.Bill;
import com.vehicle.service.entity.Booking;
import com.vehicle.service.repository.BillRepository;
import com.vehicle.service.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepo;

    @Autowired
    private BookingRepository bookingRepo;


    public void createBill(
            Long bookingId,
            double serviceCharge,
            double partsCharge) {

        Booking booking = bookingRepo
                .findById(bookingId)
                .orElseThrow();

                if (!"Completed".equals(booking.getStatus())) {
    throw new IllegalArgumentException(
            "Bill can only be created for completed bookings."
    );
}

        // Prevent duplicate bills
        if (billRepo.findByBooking(booking).isPresent()) {
            throw new IllegalArgumentException(
                    "Bill already exists for this booking."
            );
        }

        Bill bill = new Bill();

        bill.setBooking(booking);
        bill.setServiceCharge(serviceCharge);
        bill.setPartsCharge(partsCharge);

        double total =
                serviceCharge + partsCharge;

        bill.setTotalAmount(total);

        bill.setPaymentStatus("Unpaid");

        billRepo.save(bill);
    }
    
    public Bill getBillById(Long billId) {

    return billRepo
            .findById(billId)
            .orElse(null);
}


public void markAsPaid(Long billId) {

    Bill bill = billRepo
            .findById(billId)
            .orElseThrow();

    bill.setPaymentStatus("Paid");

    billRepo.save(bill);
}
}