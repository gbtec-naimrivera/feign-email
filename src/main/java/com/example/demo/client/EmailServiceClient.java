package com.example.demo.client;

import com.example.demo.dto.EmailResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>Feign client interface for interacting with an external email service.</p>
 * <p>This interface defines methods to fetch email details by ID and retrieve all emails.</p>
 * <p>It is used to communicate with a remote email service using the Feign client.</p>
 */
@FeignClient(value = "feignDemo", url = "http://localhost:8080")
public interface EmailServiceClient {

    /**
     * <p>Retrieves the details of an email by its ID.</p>
     * <p>This method sends a GET request to the remote email service to fetch a specific email by its ID.</p>
     *
     * @param id the ID of the email to retrieve.
     * @return a {@link ResponseEntity} containing an {@link EmailResponseDTO} object with the email details.
     */
    @GetMapping("/email/{id}")
    ResponseEntity<EmailResponseDTO> getEmailById(@PathVariable Long id);

    /**
     * <p>Retrieves all emails from the email service.</p>
     * <p>This method sends a GET request to fetch all emails from the remote service and returns a list of emails.</p>
     *
     * @return a {@link ResponseEntity} containing a list of {@link EmailResponseDTO} objects with email details.
     */
    @GetMapping("/emails")
    ResponseEntity<List<EmailResponseDTO>> getAllEmails();
}
