package com.gbtec.interview.email_server.logic;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.logic.EmailService;
import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import com.gbtec.interview.email_server.persistence.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

public abstract class BaseTest {


    @Autowired
    EmailService emailService;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    EmailRepository emailRepository;


}
