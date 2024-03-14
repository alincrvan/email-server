package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.communication.dto.EmailCollectionDTO;
import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import com.gbtec.interview.email_server.exception.EmailNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BulkEmailServiceTest extends BaseTest {


    @Autowired
    BulkEmailService bulkEmailService;


    Email email1;
    Email email2;

    @BeforeEach
    void init() {


        email1 = new Email(1L, "muster1@gmx.com", Set.of(new Recipient("reciepnt@gc1.com")), EmailState.DRAFT);

        email2 = new Email(2L, "muster2@gmx.com", Set.of(new Recipient("reciepnt@gc2.com")), EmailState.DRAFT);

    }


    @Test
    void createMultiEmails() {


        EmailDTO emailDTO1 = new EmailDTO("Muster1DTO@gmx.com");
        EmailDTO emailDTO2 = new EmailDTO("Muster2DTO@gmx.com");

        List<EmailDTO> emailDTOList = List.of(emailDTO1, emailDTO2);
        EmailCollectionDTO emailCollectionDTO = new EmailCollectionDTO
                (emailDTOList);

        List<Email> emails = emailDTOList.stream()
                .map(emailDTO -> modelMapper.map(emailDTO, Email.class))
                .toList();

        Mockito.when(emailRepository.saveAll(emails)).thenReturn(emails);


        EmailCollectionDTO result = bulkEmailService.createMultiEmails(emailCollectionDTO);

        Mockito.verify(emailRepository, times(1)).saveAll(emails);


        assertEquals(emailDTOList.size(), result.getEmails().size());

    }



    @Test
    void findMultiEmails() {

        List<Email> emailList = List.of(
             email1,email2);


        List<Long> ids = List.of(1L, 2L);
        Mockito.when(emailRepository.findAllById(ids)).thenReturn(emailList);

        bulkEmailService.findMultiEmails(ids);

        Mockito.verify(emailRepository).findAllById(ids);

        Mockito.verify(modelMapper, times(2)).map(any(Email.class), Mockito.eq(EmailDTO.class));
    }

    @Test
    void deleteMultiEmails() {


        List<Email> emailList = List.of(email1, email2);
        List<Long> ids = List.of(1L, 2L);


        Mockito.when(emailRepository.findAllById(ids)).thenReturn(emailList);

        bulkEmailService.deleteMultiEmails(ids);

        Mockito.verify(emailRepository, times(1)).findAllById(ids);

        for (Email email : emailList) {
            if (email.getState() != EmailState.DELETED) {
                email.setState(EmailState.DELETED);
            }
        }
        Mockito.verify(emailRepository, times(1)).saveAll(emailList);

    }



    @Test
    void findMultiEmails_WithInvalidIds() {


        List<Email> emailList = List.of(email1, email2);
        List<Long> ids = List.of(1L, 2L);
        List<Long> invalidIds = List.of(1L, 2L, 3L);

        Mockito.when(emailRepository.findAllById(invalidIds)).thenReturn(emailList);


        assertThrows(EmailNotFoundException.class, () -> bulkEmailService.deleteMultiEmails(invalidIds), "Expected EmailNotFoundException to be thrown");


        Mockito.verify(emailRepository, times(1)).findAllById(invalidIds);

        Mockito.verify(emailRepository, times(0)).saveAll(any());


    }





}