package com.example.demo.BackupService;

import com.example.demo.client.EmailServiceClient;
import com.example.demo.dto.EmailResponseDTO;
import com.example.demo.entity.Senders;
import com.example.demo.repository.SendersRepository;
import com.example.demo.repositoryElastic.SendersRepositoryElastic;
import com.example.demo.service.BackupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BackupServiceTest {

    @InjectMocks
    private BackupService backupService;

    @Mock
    private EmailServiceClient emailServiceClient;

    @Mock
    private SendersRepository sendersRepository;

    @Mock
    private SendersRepositoryElastic sendersRepositoryElastic;

    @Test
    void testSaveSender_Success() {
        EmailResponseDTO email1 = new EmailResponseDTO(1L, "test1@example.com", "Body1", 1, null, null);
        EmailResponseDTO email2 = new EmailResponseDTO(2L, "test2@example.com", "Body2", 1, null, null);
        List<EmailResponseDTO> emailList = Arrays.asList(email1, email2);

        when(emailServiceClient.getAllEmails()).thenReturn(new ResponseEntity<>(emailList, HttpStatus.OK));

        List<String> result = backupService.saveSender();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("test1@example.com"));
        assertTrue(result.contains("test2@example.com"));

        ArgumentCaptor<List<Senders>> captor = ArgumentCaptor.forClass(List.class);
        verify(sendersRepository).saveAll(captor.capture());
        verify(sendersRepositoryElastic).saveAll(captor.capture());

        List<Senders> savedSenders = captor.getValue();
        assertEquals(2, savedSenders.size());
        assertEquals("test1@example.com", savedSenders.get(0).getEmailFrom());
        assertEquals("test2@example.com", savedSenders.get(1).getEmailFrom());
    }

    @Test
    void saveSender_Failure_ResponseNotSuccessful() {
        when(emailServiceClient.getAllEmails()).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        List<String> result = backupService.saveSender();

        assertNull(result);

        verify(sendersRepository, never()).saveAll(any());
        verify(sendersRepositoryElastic, never()).saveAll(any());
    }

    @Test
    void saveLastEmail_Success() {
        EmailResponseDTO email = new EmailResponseDTO(1L, "test@example.com", "Body", 1, null, null);
        when(emailServiceClient.getEmailById(1L)).thenReturn(new ResponseEntity<>(email, HttpStatus.OK));

        String result = backupService.saveLastEmail(1L);

        assertNotNull(result);
        assertTrue(result.contains("test@example.com"));

        ArgumentCaptor<Senders> captor = ArgumentCaptor.forClass(Senders.class);
        verify(sendersRepository).save(captor.capture());
        verify(sendersRepositoryElastic).save(captor.capture());

        Senders savedSender = captor.getValue();
        assertEquals("test@example.com", savedSender.getEmailFrom());
    }

    @Test
    void saveLastEmail_NoMessageReceived() {
        String result = backupService.saveLastEmail(null);

        assertEquals("No message received yet!", result);

        verify(sendersRepository, never()).save(any());
        verify(sendersRepositoryElastic, never()).save(any());
    }

    @Test
    void saveLastEmail_InvalidMessageFormat() {
        try {
            backupService.saveLastEmail(Long.parseLong("invalid_id"));
        } catch (NumberFormatException ex) {
            assertEquals("For input string: \"invalid_id\"", ex.getMessage());
        }

        verify(sendersRepository, never()).save(any());
        verify(sendersRepositoryElastic, never()).save(any());
    }
}
