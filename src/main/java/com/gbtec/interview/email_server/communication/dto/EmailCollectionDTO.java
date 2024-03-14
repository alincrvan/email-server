package com.gbtec.interview.email_server.communication.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class EmailCollectionDTO {

    private List< EmailDTO> emails;

    public EmailCollectionDTO(List<EmailDTO> emails) {
        this.emails = emails;
    }
}
