package com.example.demo.service;

import com.example.demo.client.EmailServiceClient;
import com.example.demo.dto.EmailResponseDTO;
import com.example.demo.entity.Senders;
import com.example.demo.repository.SendersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Service class that handles the logic of interacting with the email service and saving unique senders to the database.</p>
 * <p>This class retrieves email data via a Feign client, processes the senders, and stores them in a database.</p>
 */
@Service
public class FeignService {

    /**
     * <p>Feign client for accessing external email service.</p>
     */
    @Autowired
    private EmailServiceClient emailServiceClient;

    /**
     * <p>Repository for interacting with the {@link Senders} entity in the database.</p>
     */
    @Autowired
    private SendersRepository sendersRepository;

    @Autowired
    private MessageStorageService messageStorageService;

    /**
     * <p>Fetches all emails from the external email service and extracts unique senders.</p>
     * <p>The senders' email addresses are stored in the {@link Senders} table in the database.</p>
     *
     * @return a list of unique sender email addresses.
     * @throws RuntimeException if there is an error retrieving emails or saving senders.
     */
    @Transactional
    public List<String> saveSender() {

        ResponseEntity<List<EmailResponseDTO>> response = emailServiceClient.getAllEmails();

        if (response.getStatusCode().is2xxSuccessful()) {
            List<EmailResponseDTO> emails = response.getBody();

            List<String> senders = emails.stream()
                    .map(EmailResponseDTO::getEmailFrom)
                    .distinct()
                    .collect(Collectors.toList());

            List<Senders> sendersToSave = new ArrayList<>();

            for (String email : senders) {
                Senders sender = new Senders();
                sender.setEmailFrom(email);
                sendersToSave.add(sender);
            }

            sendersRepository.saveAll(sendersToSave);

            return senders;

        } else {
            System.err.println("Error retrieving emails from the service.");
        }
        return null;
    }

    /**
     * <p>This method retrieves the last message stored from RabbitMQ, interprets it as an email ID,
     * and then uses the Feign client to fetch the corresponding email details.</p>
     * <p>If the message is not yet received or is in an invalid format, appropriate error messages are returned.</p>
     * <p>This method also saved the sender on the database.</p>
     * @return a formatted string containing the email sender and its ID, or an error message if the operation fails.
     */
    @Transactional
    public String getAndSaveLastEmail(){

        String message = messageStorageService.getLastMessage();

        if (message == null) {
            return "No message received yet!";
        }

        Long emailId = Long.parseLong(message);
        EmailResponseDTO email = emailServiceClient.getEmailById(emailId).getBody();

        Senders sender = new Senders();
        sender.setEmailFrom(email.getEmailFrom());
        sendersRepository.save(sender);

        return "Email from " + email.getEmailFrom() + " with id " + email.getEmailId() + " working on feign";

    }
}
