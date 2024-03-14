package com.gbtec.interview.email_server.schedule;

import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {

    private final EmailRepository emailRepository;
    private final String spamAddress;

    public Scheduler(EmailRepository emailRepository, @Value("${values.spam}") String spamAddress) {
        this.emailRepository = emailRepository;
        this.spamAddress = spamAddress;
    }


    @Scheduled(cron = "${values.cron}")
    final void markAsSpam() {

        emailRepository.updateStateByEmailBodyContaining(spamAddress, EmailState.SPAM);
    }

}
