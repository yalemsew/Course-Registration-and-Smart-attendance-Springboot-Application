package edu.miu.courseregistrationcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailEventDTO {
    private String subject;
    private String body;
    private String sender;
    private String receiver;
    private String cc;
    private String bcc;
}
