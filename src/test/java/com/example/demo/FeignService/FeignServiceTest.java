package com.example.demo.FeignService;

import com.example.demo.client.EmailServiceClient;
import com.example.demo.dto.EmailResponseDTO;
import com.example.demo.entity.Senders;
import com.example.demo.repository.SendersRepository;
import com.example.demo.service.FeignService;
import com.example.demo.service.MessageStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeignServiceTest {

    /*@InjectMocks
    private FeignService feignService;

    @Mock
    private EmailServiceClient emailServiceClient;

    @Mock
    private SendersRepository sendersRepository;

    @Mock
    private MessageStorageService messageStorageService;

    @Test
    void testSaveSender_Success() {

        EmailResponseDTO email1 = new EmailResponseDTO(1L, "test1@example.com", "Body1", 1, null, null);
        EmailResponseDTO email2 = new EmailResponseDTO(2L, "test2@example.com", "Body2", 1, null, null);
        List<EmailResponseDTO> emailList = Arrays.asList(email1, email2);

        when(emailServiceClient.getAllEmails()).thenReturn(new ResponseEntity<>(emailList, HttpStatus.OK));

        List<String> result = feignService.saveSender();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("test1@example.com"));
        assertTrue(result.contains("test2@example.com"));

        ArgumentCaptor<List<Senders>> captor = ArgumentCaptor.forClass(List.class);
        verify(sendersRepository).saveAll(captor.capture());

        List<Senders> savedSenders = captor.getValue();
        assertEquals(2, savedSenders.size());
        assertEquals("test1@example.com", savedSenders.get(0).getEmailFrom());
        assertEquals("test2@example.com", savedSenders.get(1).getEmailFrom());
    }


    @Test
    void testGetAndSaveLastEmail_Success() {

        when(messageStorageService.getLastMessage()).thenReturn("1");

        EmailResponseDTO email = new EmailResponseDTO(1L, "test@example.com", "Body", 1, null, null);
        when(emailServiceClient.getEmailById(1L)).thenReturn(new ResponseEntity<>(email, HttpStatus.OK));

        String result = feignService.getAndSaveLastEmail();

        ArgumentCaptor<Senders> captor = ArgumentCaptor.forClass(Senders.class);
        verify(sendersRepository).save(captor.capture());

        Senders savedSender = captor.getValue();
        assertEquals("test@example.com", savedSender.getEmailFrom());
    }*/

}