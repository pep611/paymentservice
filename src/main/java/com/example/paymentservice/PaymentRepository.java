package com.example.paymentservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentModel, Integer> {
    boolean existsByBookingNumber(Integer bookingNumber);
}
