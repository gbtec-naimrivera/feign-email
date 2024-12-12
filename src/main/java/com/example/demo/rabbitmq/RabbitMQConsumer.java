package com.example.demo.rabbitmq;

import com.example.demo.service.MessageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Service for consuming messages from RabbitMQ.</p>
 * <p>This service listens to a specific queue, processes incoming messages, and stores the last received message.</p>
 */
@Service
public class RabbitMQConsumer {

    /**
     * Service for storing and retrieving the last received message.
     */
    @Autowired
    private MessageStorageService messageStorageService;

    /**
     * Logger instance for logging information about consumed messages.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * Listens to messages from the configured RabbitMQ queue.
     * <p>The received message is logged and stored using {@link MessageStorageService}.</p>
     *
     * @param message the message received from the RabbitMQ queue.
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consume(String message) {
        LOGGER.info(String.format("Received message -> %s", message));
        messageStorageService.setLastMessage(message);
    }
}
