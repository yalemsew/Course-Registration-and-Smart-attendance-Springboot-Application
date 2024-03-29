package edu.miu.courseregistrationemail.controller;

import edu.miu.courseregistrationcommon.dto.MailEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/mail")
@Slf4j
public class MailSenderController {

    @Autowired
    private KafkaTemplate<String, MailEventDTO> mailEventKafkaTemplate;

    @PostMapping("/send")
    public void sendMail(@RequestBody MailEventDTO mailEventDTO) {
        CompletableFuture<SendResult<String, MailEventDTO>> future = mailEventKafkaTemplate.send("mail", mailEventDTO);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + mailEventDTO +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=["
                        + mailEventDTO + "] due to : " + ex.getMessage());
            }
        });
    }
}
