package edu.miu.courseregistrationcore.integration.listener;

import edu.miu.courseregistrationcommon.dto.MailEventDTO;
import edu.miu.courseregistrationcore.service.IMailEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class MailListener {
    private final IMailEventService mailEventService;

    @KafkaListener(topics = {"mail"})
    public void listen(MailEventDTO event) {
        log.info("Received mail event {}", event);
        mailEventService.createMailEvent(event);
    }
}
