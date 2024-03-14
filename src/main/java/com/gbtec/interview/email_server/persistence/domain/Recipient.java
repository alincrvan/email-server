package com.gbtec.interview.email_server.persistence.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Recipient {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Email
    private String email;


    public Recipient(String email) {
        this.email = email;
    }


}
