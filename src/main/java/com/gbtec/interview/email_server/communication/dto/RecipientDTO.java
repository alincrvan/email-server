package com.gbtec.interview.email_server.communication.dto;

import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RecipientDTO {
    @Email
    private String email;
}
