package com.gbtec.interview.email_server.persistence.repository;

import com.gbtec.interview.email_server.persistence.domain.Email;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findByEmailBody(String emailBody);


    @Modifying
    @Transactional
    @Query("UPDATE Email e SET e.state = :spamState WHERE e.emailBody LIKE %:targetEmail% AND e.state != :spamState")
    void updateStateByEmailBodyContaining(String targetEmail, EmailState spamState);


//    @Transactional
//    @Modifying
//    @Query("UPDATE Email e SET e.state = 'DELETED' WHERE e.id IN :emailIds AND e.id IN (SELECT id FROM Email WHERE id IN :emailIds)")
//    int updateEmailStateByIdsIfExist(List<Long> emailIds, EmailState state);
}
