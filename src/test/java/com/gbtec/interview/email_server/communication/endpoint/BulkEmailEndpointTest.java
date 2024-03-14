package com.gbtec.interview.email_server.communication.endpoint;

import com.gbtec.interview.email_server.communication.dto.EmailCollectionDTO;
import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.logic.BulkEmailService;
import com.gbtec.interview.email_server.logic.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BulkEmailEndpointTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    BulkEmailService bulkEmailService;

    String url = "/emails/bulk";
    List<Long> ids = List.of(1L, 2L, 3L);


    @Test
    void getMultiEmails() {
        String requestParam = "?ids-to-query=1,2,3";
        testRestTemplate.getForObject(url + requestParam, EmailDTO[].class);
        Mockito.verify(bulkEmailService
        ).findMultiEmails(ids);

    }

    @Test
    void addMultiEmails() {

        EmailCollectionDTO emailCollectionDTO =
                new EmailCollectionDTO();

        testRestTemplate.postForObject(url, emailCollectionDTO, EmailCollectionDTO.class);
        Mockito.verify(bulkEmailService
        ).createMultiEmails(emailCollectionDTO);
    }


    @Test
    void deleteMultiEmails() {

        String requestParam = "?ids-to-delete=1,2,3";
        testRestTemplate.delete(url + requestParam);
        Mockito.verify(bulkEmailService
        ).deleteMultiEmails(ids);
    }
}