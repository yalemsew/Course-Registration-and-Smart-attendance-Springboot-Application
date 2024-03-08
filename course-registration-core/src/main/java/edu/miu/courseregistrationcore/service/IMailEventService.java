package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcommon.dto.MailEventDTO;

import java.util.List;

public interface IMailEventService {
    MailEventDTO getMailEventId(Long id);

    List<MailEventDTO> getAllMails();

    MailEventDTO createMailEvent(MailEventDTO mailEvent);

    MailEventDTO updateMailEvent(MailEventDTO mailEvent);

    void deleteMailEvent(Long id);

}
