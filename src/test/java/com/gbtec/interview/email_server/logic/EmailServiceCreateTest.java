package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmailServiceCreateTest extends BaseTest {


    Email email1;

    EmailDTO emailDTO;


    @BeforeEach
    void init() {


        email1 = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.DRAFT);


        emailDTO = new EmailDTO("DTOmuster@gmx.com");

    }


    @Test
    void create() {
        Email mappedEmail = new Email("muster1@gmx");

        Mockito.when(modelMapper.map(emailDTO, Email.class)).thenReturn(mappedEmail);
        Mockito.when(emailRepository.save(mappedEmail)).thenReturn(email1);

        emailService.create(emailDTO);

        Mockito.verify(modelMapper).map(emailDTO, Email.class);
        Mockito.verify(emailRepository).save(mappedEmail);
        Mockito.verify(modelMapper).map(email1, EmailDTO.class);

    }


    @Test
    void create_nullInput() {

        EmailDTO emptyDTO = new EmailDTO();
        Email emptyEmail = new Email();

        Mockito.when(modelMapper.map(emptyDTO, Email.class)).thenReturn(emptyEmail);


        Mockito.when(emailRepository.save(emptyEmail)).thenThrow(new IllegalArgumentException("Email must not be null"));


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.create(emptyDTO);
        });

        Mockito.verify(emailRepository).save(Mockito.any(Email.class));


    }


}