package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.communication.dto.EmailCollectionDTO;
import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.repository.EmailRepository;
import com.gbtec.interview.email_server.exception.EmailNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BulkEmailService {

    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;

    public BulkEmailService(EmailRepository emailRepository, ModelMapper modelMapper) {
        this.emailRepository = emailRepository;
        this.modelMapper = modelMapper;
    }

    public EmailCollectionDTO createMultiEmails(EmailCollectionDTO emailCollectionDTO) {

        List<Email> emails = emailCollectionDTO.getEmails().stream()
                .map(emailDTO -> modelMapper.map(emailDTO, Email.class)).toList();

        List<Email> emailSaved = emailRepository.saveAll(emails);


        List<EmailDTO> emailDTOS = emailSaved.stream()
                .map(email -> modelMapper.map(email, EmailDTO.class)).toList();

        emailCollectionDTO.setEmails(emailDTOS);

        return emailCollectionDTO;
    }


    public List<EmailDTO> findMultiEmails(List<Long> emailIds) {

        List<Email> emails = emailRepository.findAllById(emailIds);

        if (emails.size() != emailIds.size()) {
            throw new EmailNotFoundException();
        }

        List<EmailDTO> emailDTOs = emails.stream()
                .map(email -> modelMapper.map(email, EmailDTO.class)).toList();

        return emailDTOs;

    }


    @Transactional
    public void deleteMultiEmails(List<Long> emailIds) {

        List<Email> emailsToDelete = emailRepository.findAllById(emailIds);

        if (emailsToDelete.size() != emailIds.size()) {
            throw new EmailNotFoundException();
        }

        for (Email email : emailsToDelete) {
            if (email.getState() != EmailState.DELETED) {
                email.setState(EmailState.DELETED);
            }
        }
        emailRepository.saveAll(emailsToDelete);

    }


}
