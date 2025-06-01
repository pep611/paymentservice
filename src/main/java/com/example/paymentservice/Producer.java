package com.example.paymentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer
{
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "inventory-events";

    @Autowired //DEPENDENCY INJECTION PROMISE FULFILLED AT RUNTIME
    private KafkaTemplate<String, String> kafkaTemplate ;

    public void sendPaymentConfirmation(PaymentEvent paymentEvent) throws JsonProcessingException // LOGIN | REGISTER
    {
        // convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String datum = objectMapper.writeValueAsString(paymentEvent);

        logger.info(String.format("#### -> Producing Inventory message -> %s", datum));
        this.kafkaTemplate.send(TOPIC,"1", datum);
    }

    public void sendPaymentConfirmation(int bookingId, String busNumber, int requestedSeats) {
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setBookingId(bookingId);
        paymentEvent.setBusNumber(busNumber);
        paymentEvent.setNoOfSeats(requestedSeats);

        try {
            sendPaymentConfirmation(paymentEvent);
        } catch (JsonProcessingException e) {
            logger.error("Error sending payment confirmation", e);
        }
    }
}
