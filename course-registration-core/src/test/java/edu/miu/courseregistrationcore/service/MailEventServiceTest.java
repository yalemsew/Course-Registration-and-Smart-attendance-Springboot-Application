package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcommon.dto.MailEventDTO;
import edu.miu.courseregistrationcore.domain.MailEvent;
import edu.miu.courseregistrationcore.repository.MailEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MailEventServiceTest {

    @Mock
    private MailEventRepository mailEventRepository;

    @InjectMocks
    private MailEventService mailEventService;

    @Test
    public void testCreateMailEvent() {
        // Setup test data
        MailEventDTO mailEventDTO = MailEventDTO.builder()
                .body("Test body")
                .bcc("testbcc@example.com")
                .cc("testcc@example.com")
                .subject("Test subject")
                .sender("testsender@example.com")
                .receiver("testreceiver@example.com")
                .build();

        MailEvent mailEvent = new MailEvent();
        BeanUtils.copyProperties(mailEventDTO, mailEvent);

        MailEvent savedMailEvent = new MailEvent();
        BeanUtils.copyProperties(mailEventDTO, savedMailEvent);
        savedMailEvent.setId(1L); // Assuming there is an ID field

        // Mock the repository save method
        when(mailEventRepository.save(any(MailEvent.class))).thenReturn(savedMailEvent);

        // Call the service method
        MailEventDTO result = mailEventService.createMailEvent(mailEventDTO);

        // Assert the results
        assertEquals(mailEventDTO.getBody(), result.getBody());
        assertEquals(mailEventDTO.getBcc(), result.getBcc());
        assertEquals(mailEventDTO.getCc(), result.getCc());
        assertEquals(mailEventDTO.getSubject(), result.getSubject());
        assertEquals(mailEventDTO.getSender(), result.getSender());
        assertEquals(mailEventDTO.getReceiver(), result.getReceiver());
    }
}
