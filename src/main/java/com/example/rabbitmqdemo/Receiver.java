package com.example.rabbitmqdemo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    static final String queueName = "spring-boot";

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = {queueName})
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
