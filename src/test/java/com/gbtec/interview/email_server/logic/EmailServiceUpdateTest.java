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

import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmailServiceUpdateTest extends BaseTest {

    Long id = 1L;

    Email email1;

    EmailDTO emailDTO;

    @BeforeEach
    void init() {


        email1 = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.DRAFT);


        emailDTO = new EmailDTO("DTOmuster@gmx.com");

    }


    @Test
    void update() {
        Email updatedEmail = new Email(1L, "UpdatedMuster@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.DRAFT);

        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.ofNullable(email1));
        Mockito.when(emailRepository.save(email1)).thenReturn(updatedEmail);

        emailService.update(emailDTO, id);

        Mockito.verify(emailRepository).findById(id);
        Mockito.verify(modelMapper).map(emailDTO, email1);
        Mockito.verify(emailRepository).save(email1);
    }


    @Test
    void update_emailNotDraft() {

        Email sentEmail = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.SENT);

        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.of(sentEmail));


        Assertions.assertThrows(EmailNotFoundException.class, () -> {
            emailService.update(emailDTO, id);
        });


        Mockito.verify(emailRepository, Mockito.never()).save(Mockito.any(Email.class));
    }


}