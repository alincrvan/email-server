package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import com.gbtec.interview.email_server.exception.EmailNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmailServiceSendTest extends BaseTest {

    Long id = 1L;

    @Test
    void send() {

        Email sentEmail = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.SENT);

        Email draftEmail = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("recipient@gc1.com")), EmailState.DRAFT);

        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.of(draftEmail));

        Mockito.when(emailRepository.save(draftEmail)).thenReturn(sentEmail);

        emailService.send(id);

        Mockito.verify(emailRepository).findById(id);

        Mockito.verify(emailRepository).save(Mockito.argThat(
                savedEmail -> savedEmail.getState() == EmailState.SENT
        ));
    }


    @Test
    void send_emailAlreadySent() {

        Email sentEmail = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.SENT);

        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.of(sentEmail));


        Assertions.assertThrows(EmailNotFoundException.class, () -> {
            emailService.send(id);
        });


        Mockito.verify(emailRepository, Mockito.never()).save(Mockito.any(Email.class));
    }

    @Test
    void send_emailToEmpty() {

        Email draftEmail = new Email(1L, "muster1@gmx.com", Collections.emptySet(), EmailState.DRAFT);
        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.of(draftEmail));


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.send(id);
        });


        Mockito.verify(emailRepository, Mockito.never()).save(Mockito.any(Email.class));
    }


}