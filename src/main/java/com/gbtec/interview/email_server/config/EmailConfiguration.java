package com.gbtec.interview.email_server.config;

import com.gbtec.interview.email_server.communication.dto.EmailDTO;
import com.gbtec.interview.email_server.persistence.domain.Email;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class EmailConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        modelMapper.addMappings(new PropertyMap<EmailDTO, Email>() {
            @Override
            protected void configure() {
                skip(destination.getState());
            }
        });


        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }


}
