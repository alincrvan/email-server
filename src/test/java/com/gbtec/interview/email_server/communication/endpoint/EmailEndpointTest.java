package com.gbtec.interview.email_server.communication.endpoint;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.communication.dto.RecipientDTO;
import com.gbtec.interview.email_server.logic.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmailEndpointTest {


    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    EmailService emailService;

    String url = "/emails";
    Long id = 1L;

    @Test
    void getAllEmails() {

        testRestTemplate.getForObject(url, EmailDTO[].class);
        Mockito.verify(emailService).findAll();
    }

    @Test
    void getEmail() {

        testRestTemplate.getForObject(url+"/"+id, EmailDTO.class);
        Mockito.verify(emailService).find(id);
    }

    @Test
    void createEmail() {
        EmailDTO emailDTO = new EmailDTO("any@muster.com");
        testRestTemplate.postForObject(url, emailDTO, EmailDTO.class);
        Mockito.verify(emailService).create(emailDTO);
    }

    @Test
    void sendEmail() {

        testRestTemplate.patchForObject(url+"/"+id+"/status",null,Void.class);
        Mockito.verify(emailService).send(id);
    }

    @Test
    void updateEmail() {
        EmailDTO emailDTO = new EmailDTO("any@muster.com");

        testRestTemplate.patchForObject(url+"/"+id,emailDTO,Void.class);

        Mockito.verify(emailService).update(emailDTO,id);

    }

    @Test
    void deleteEmail() {

        testRestTemplate.delete(url+"/"+id);
        Mockito.verify(emailService).delete(id);
    }
}