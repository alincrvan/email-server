package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmailServiceDeleteTest extends BaseTest {

    Email draftEmail;
    Email deletedEmail;

    Long id = 1L;

    @BeforeEach
    void init() {


        draftEmail = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.DRAFT);

        deletedEmail = new Email(2L, "muster2@gmx.com", Set.of(new Recipient("reciepnt@gc2.com")), EmailState.DELETED);

    }


    @Test
    void delete() {



        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.of(draftEmail));

        Mockito.when(emailRepository.save(draftEmail)).thenReturn(deletedEmail);

        emailService.delete(id);

        Mockito.verify(emailRepository).findById(id);

        Mockito.verify(emailRepository, Mockito.times(draftEmail.getState() == EmailState.DRAFT ? 1 : 0)).save(deletedEmail);
    }




    @Test
    void delete_emailAlreadyDeleted() {



        Mockito.when(emailRepository.findById(id)).thenReturn(Optional.of(deletedEmail));


        emailService.delete(id);

        Mockito.verify(emailRepository).findById(id);

        Mockito.verify(emailRepository, Mockito.never()).save(Mockito.any(Email.class));
    }




}