package com.Cryptopay.Config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@RequiredArgsConstructor
@Getter
@Setter

public class TwilioConfig {
    private String accountSid;
    private String authToken;
    private String TrialNumber;


}
