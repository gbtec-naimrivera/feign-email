package com.example.demo.rest;

import com.example.demo.client.EmailServiceClient;
import com.example.demo.dto.EmailResponseDTO;
import com.example.demo.service.FeignService;
import com.example.demo.service.MessageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Controller for handling requests related to the Feign Service.</p>
 * <p>This controller exposes endpoints for fetching email details and senders using a Feign client and service.</p>
 */
@RestController
@RequestMapping("/feignService")
public class FeignController {

    /**
     * <p>Feign client used to interact with the external email service to retrieve email details.</p>
     */
    @Autowired
    private EmailServiceClient emailServiceClient;

    @Autowired
    private MessageStorageService messageStorageService;

    /**
     * <p>Service that contains the logic to fetch and save sender information.</p>
     */
    @Autowired
    private FeignService feignService;

    /**
     * <p>Endpoint to fetch the details of a specific email by its ID.</p>
     * <p>It makes a call to the email service client to retrieve the email details, and then returns a formatted string with the email sender and ID.</p>
     *
     * @param emailId the ID of the email to fetch.
     * @return a formatted string containing the email sender and its ID.
     */
    @GetMapping("/{emailId}")
    public String getEmail(@PathVariable Long emailId) {

        EmailResponseDTO email = emailServiceClient.getEmailById(emailId).getBody();

        return "Email from " + email.getEmailFrom() + " with id " + email.getEmailId() + " working on feign";
    }

    /**
     * <p>Endpoint to fetch and print the list of email senders.</p>
     * <p>It calls the Feign service to retrieve the senders, and returns a list of senders. If no senders are found, it returns a 204 No Content status.</p>
     *
     * @return a {@link ResponseEntity} containing a list of email senders, or a 204 No Content if no senders are found.
     */
    @GetMapping("/senders")
    public ResponseEntity<List<String>> getAndPrintSenders() {
        try {
            List<String> senders = feignService.saveSender();

            if (senders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(senders);
        } catch (Exception e) {
            System.err.println("Error while fetching senders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * <p>Endpoint to fetch email details based on the last message received from RabbitMQ.</p>
     * <p>It calls the Feign service to retrieve last sender, and return a message with the senderId and sender.</p>
     * @return a formatted string containing the email sender and its ID, or an error message if the operation fails.
     */
    @GetMapping("/rabbitMQ")
    public String getLastEmail() {

        return feignService.getAndSaveLastEmail();

    }
}
