package com.example.paymentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;

@Service
public class Consumer {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private Producer producer;

    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void consumeBookingEvents(String message) throws IOException
    {
        ObjectMapper mapper  = new ObjectMapper();
        PaymentEvent datum =  mapper.readValue(message, PaymentEvent.class);
        logger.info(String.format("#### -> Received booking message: -> %s", datum));

        if (!paymentRepository.existsByBookingNumber(datum.getBookingId())) {
            PaymentModel payment = new PaymentModel();
            payment.setBookingNumber(datum.getBookingId());
            payment.setBusNumber(datum.getBusNumber());
            payment.setDateOfPayment(Instant.now());
            payment.setRequestedSeats(datum.getNoOfSeats());
            paymentRepository.save(payment);
            logger.info(String.format("#### -> Payment saved for booking number: %d", datum.getBookingId()));
            // Here you can add more logic to handle the payment processing
            // For example, you might want to update the booking status or notify other services
            // about the payment status.
            logger.info("Payment processing completed for booking number: " + datum.getBookingId());
        }
        // You can also send a message to another topic if needed
        producer.sendPaymentConfirmation(datum.getBookingId(), datum.getBusNumber(), datum.getNoOfSeats());
    }
}
