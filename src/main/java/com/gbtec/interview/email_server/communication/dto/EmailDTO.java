package com.gbtec.interview.email_server.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gbtec.interview.email_server.persistence.domain.EmailState;
import com.gbtec.interview.email_server.persistence.domain.Recipient;
import com.gbtec.interview.email_server.validation.ComposeValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class EmailDTO {

    private Long emailId;

    @Email
    @NotEmpty(groups = ComposeValidation.class)
    private String emailFrom;


    private Set<Recipient> emailTo = new HashSet<>();


    private Set<Recipient> emailCC = new HashSet<>();

    private String emailBody;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EmailState state = EmailState.DRAFT;

    public EmailDTO(String emailFrom) {
        this.emailFrom = emailFrom;
    }

}
