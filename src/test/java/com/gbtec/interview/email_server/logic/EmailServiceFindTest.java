package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import com.gbtec.interview.email_server.exception.EmailNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmailServiceFindTest extends BaseTest {


    Email email1;
    Email email2;

    EmailDTO emailDTO;

    Long id = 1L;

    @BeforeEach
    void init() {


        email1 = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.DRAFT);

        email2 = new Email(2L, "muster2@gmx.com", Set.of(new Recipient("reciepnt@gc2.com")), EmailState.DRAFT);

        emailDTO = new EmailDTO("DTOmuster@gmx.com");


    }


    @Test
    void findAll() {

        List<Email> emailList = List.of(email1, email2);
        Mockito.when(emailRepository.findAll()).thenReturn(emailList);


        emailService.findAll();

        Mockito.verify(emailRepository).findAll();
        Mockito.verify(modelMapper, Mockito.times(2)).map(Mockito.any(Email.class), Mockito.eq(EmailDTO.class));

    }

    @Test
    void find() {

        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.ofNullable(email1));

        emailService.find(id);

        Mockito.verify(emailRepository).findById(id);
        Mockito.verify(modelMapper).map(email1, EmailDTO.class);

    }


    @Test
    void emailNotFound() {

        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.empty());


        Assertions.assertThrows(EmailNotFoundException.class, () -> {
            emailService.update(emailDTO, id);
        });


        Mockito.verify(emailRepository, Mockito.never()).save(Mockito.any(Email.class));
    }


}