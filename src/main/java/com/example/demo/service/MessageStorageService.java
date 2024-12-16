package com.example.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * <p>Service for storing and retrieving the last message received from RabbitMQ.</p>
 * <p>This service acts as an in-memory storage for the most recent message received,
 * providing methods to set and get the stored message.</p>
 */
@Service
@Transactional
public class MessageStorageService {

    /**
     * Stores the last message received from RabbitMQ.
     */
    private String lastMessage;

    /**
     * Retrieves the last message stored in the service.
     *
     * @return the last message as a {@link String}, or {@code null} if no message has been stored.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Sets the last message received from RabbitMQ.
     *
     * @param message the message to store as a {@link String}.
     */
    public void setLastMessage(String message) {
        this.lastMessage = message;
    }
}