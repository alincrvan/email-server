package com.gbtec.interview.email_server.communication.endpoint;


import com.gbtec.interview.email_server.communication.dto.EmailCollectionDTO;
import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.logic.BulkEmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("emails/bulk")
public class BulkEmailEndpoint {


    private final BulkEmailService BulkEmailService;


    public BulkEmailEndpoint(BulkEmailService BulkEmailService) {
        this.BulkEmailService = BulkEmailService;

    }


    @GetMapping
    public List<EmailDTO> getMultiEmails(@NotEmpty(message = "add at least one id") @RequestParam("ids-to-query") List<Long> ids) {

        return BulkEmailService.findMultiEmails(ids);
    }


    @PostMapping
    public EmailCollectionDTO addMultiEmails(@Valid @RequestBody EmailCollectionDTO emailCollectionDTO) {
        return BulkEmailService.createMultiEmails(emailCollectionDTO);

    }


    @DeleteMapping
    public void deleteMultiEmails(@NotEmpty(message = "add at least one id") @RequestParam("ids-to-delete") List<Long> ids) {

        BulkEmailService.deleteMultiEmails(ids);
    }
}
