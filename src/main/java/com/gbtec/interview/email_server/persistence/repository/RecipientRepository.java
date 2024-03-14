package com.gbtec.interview.email_server.persistence.repository;

import com.gbtec.interview.email_server.persistence.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
