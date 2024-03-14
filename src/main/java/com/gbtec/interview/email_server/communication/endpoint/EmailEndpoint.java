package com.gbtec.interview.email_server.communication.endpoint;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.logic.EmailService;
import com.gbtec.interview.email_server.validation.ComposeValidation;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/emails")
public class EmailEndpoint {

    private final EmailService emailService;

    public EmailEndpoint(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping
    public List<EmailDTO> getAllEmails() {
        return emailService.findAll();
    }

    @GetMapping("/{id}")
    public EmailDTO getEmail(@PathVariable Long id) {
        return emailService.find(id);
    }


    @PostMapping
    public EmailDTO createEmail(@Validated(ComposeValidation.class) @RequestBody EmailDTO emailDTO) {
        return emailService.create(emailDTO);
    }


    @PatchMapping("/{id}/status")
    public void sendEmail(@PathVariable Long id) {
        emailService.send(id);
    }


    @PatchMapping("/{id}")
    public void updateEmail(@Valid @RequestBody EmailDTO emailDTO, @PathVariable Long id) {

        emailService.update(emailDTO, id);
    }


    @DeleteMapping("/{id}")
    public void deleteEmail(@PathVariable Long id) {

        emailService.delete(id);
    }


}
