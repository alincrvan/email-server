package com.gbtec.interview.email_server.persistence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gbtec.interview.email_server.validation.ComposeValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Email {

    @Id
    @GeneratedValue
    private Long emailId;

    @CreationTimestamp
    private Instant createdOn;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Instant> lastUpdatedOn = new ArrayList<>();

    @jakarta.validation.constraints.Email
    @NotEmpty(groups = ComposeValidation.class)
    private String emailFrom;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Recipient> emailTo = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Recipient> emailCC = new HashSet<>();


    private String emailBody;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private EmailState state = EmailState.DRAFT;


    public Email(Long emailId, String emailFrom, Set<Recipient> emailTo, EmailState state) {
        this.emailId = emailId;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.state = state;
    }

    public Email(String emailFrom) {
        this.emailFrom = emailFrom;
    }


}
