package com.example.paymentservice;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "payment_model")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_number", nullable = false)
    private Integer id;

    @Column(name = "booking_number")
    private Integer bookingNumber;

    @Column(name = "date_of_payment")
    private Instant dateOfPayment;

    @Column(name = "bus_number", length = 20)
    private String busNumber;

    @Column(name = "requested_seats")
    private Integer requestedSeats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Instant getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Instant dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
    public Integer getRequestedSeats() {
        return requestedSeats;
    }

    public void setRequestedSeats(Integer requestedSeats) {
        this.requestedSeats = requestedSeats;
    }
}