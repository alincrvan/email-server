package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.repository.EmailRepository;
import com.gbtec.interview.email_server.exception.EmailNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;

    public EmailService(EmailRepository emailRepository, ModelMapper modelMapper) {
        this.emailRepository = emailRepository;
        this.modelMapper = modelMapper;
    }

    public List<EmailDTO> findAll() {

        List<EmailDTO> emailDTOList = emailRepository.findAll().stream()
                .map(email -> modelMapper.map(email, EmailDTO.class))
                .collect(toList());

        return emailDTOList;
    }


    public EmailDTO find(Long id) {

        Email foundEmail = getEmailByIdOrThrow(id);

        return modelMapper.map(foundEmail, EmailDTO.class);

    }


    public EmailDTO create(EmailDTO emailDTO) {

        Email email = modelMapper.map(emailDTO, Email.class);

        Email savedEmail = emailRepository.save(email);

        return modelMapper.map(savedEmail, EmailDTO.class);

    }


    public void send(Long id) {

        Email foundEmail = getEmailByIdOrThrow(id);

        if (!foundEmail.getState().equals(EmailState.DRAFT)) {
            throw new EmailNotFoundException();
        }
        if (foundEmail.getEmailTo().isEmpty()) {
            throw new IllegalArgumentException("EmailTo field is empty");
        }

        foundEmail.setState(EmailState.SENT);

        emailRepository.save(foundEmail);

    }


    public void update(EmailDTO emailDTO, Long id) {
        //JPA: findByIdAndState(Long id, EmailState state);
        Email foundEmail = getEmailByIdOrThrow(id);

        if (!foundEmail.getState().equals(EmailState.DRAFT)) {
            throw new EmailNotFoundException();
        }

        modelMapper.map(emailDTO, foundEmail);

        foundEmail.getLastUpdatedOn().add(Instant.now());

        emailRepository.save(foundEmail);

    }

    public void delete(Long id) {

        Email foundEmail = getEmailByIdOrThrow(id);

        if (foundEmail.getState() != EmailState.DELETED) {

            foundEmail.setState(EmailState.DELETED);

            emailRepository.save(foundEmail);
        }
    }


    private Email getEmailByIdOrThrow(Long id) {
        Email foundEmail = emailRepository.findById(id)
                .orElseThrow(EmailNotFoundException::new);
        return foundEmail;
    }
}
