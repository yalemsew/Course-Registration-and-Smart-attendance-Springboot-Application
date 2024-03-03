package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcommon.dto.MailEventDTO;
import edu.miu.courseregistrationcore.domain.MailEvent;
import edu.miu.courseregistrationcore.repository.MailEventRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MailEventService implements IMailEventService {
    @Autowired
    private MailEventRepository mailEventRepository;

    @Override
    public MailEventDTO getMailEventId(Long id) {
        Optional<MailEvent> byId = mailEventRepository.findById(id);
        return MailEventDTO.builder()
                .bcc(byId.map(MailEvent::getBcc).orElse(null))
                .cc(byId.map(MailEvent::getCc).orElse(null))
                .body(byId.map(MailEvent::getBody).orElse(null))
                .subject(byId.map(MailEvent::getSubject).orElse(null))
                .sender(byId.map(MailEvent::getSender).orElse(null))
                .receiver(byId.map(MailEvent::getReceiver).orElse(null))
                .build();
    }

    @Override
    public List<MailEventDTO> getAllMails() {
        List<MailEvent> all = mailEventRepository.findAll();
        List<MailEventDTO> dtos = new ArrayList<>();
        for (MailEvent mailEvent : all) {
            dtos.add(MailEventDTO.builder()
                    .body(mailEvent.getBody())
                    .bcc(mailEvent.getBcc())
                    .cc(mailEvent.getCc())
                    .subject(mailEvent.getSubject())
                    .sender(mailEvent.getSender())
                    .receiver(mailEvent.getReceiver())
                    .build()
            );
        }
        return dtos;
    }

    @Override
    public MailEventDTO createMailEvent(MailEventDTO mailEvent) {
        MailEvent mailEvent1 = new MailEvent();
        BeanUtils.copyProperties(mailEvent, mailEvent1);
        MailEvent save = mailEventRepository.save(mailEvent1);
        return MailEventDTO.builder()
                .body(save.getBody())
                .bcc(save.getBcc())
                .cc(save.getCc())
                .subject(save.getSubject())
                .sender(save.getSender())
                .receiver(save.getReceiver())
                .build();
    }

    @Override
    public MailEventDTO updateMailEvent(MailEventDTO mailEvent) {
        MailEvent mailEvent1 = new MailEvent();
        BeanUtils.copyProperties(mailEvent, mailEvent1);
        MailEvent save = mailEventRepository.save(mailEvent1);
        return MailEventDTO.builder()
                .body(save.getBody())
                .bcc(save.getBcc())
                .cc(save.getCc())
                .subject(save.getSubject())
                .sender(save.getSender())
                .receiver(save.getReceiver())
                .build();
    }

    @Override
    public void deleteMailEvent(Long id) {
        mailEventRepository.deleteById(id);
    }
}
