package com.Cryptopay.Service;

import com.Cryptopay.Config.TwilioConfig;
import com.Cryptopay.Entity.ConfirmationCode;
import com.Cryptopay.Repository.ConfirmationCodeRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationCodeService {

    @Autowired
    private final ConfirmationCodeRepository repository;
    @Autowired
    private final TwilioConfig twilioConfig;

    public void saveCode(ConfirmationCode code){
        repository.save(code);
    }

    public Optional<ConfirmationCode> getCode(String code) {
        return repository.findByCode(code);
    }

    public int setConfirmedAt(String code) {
        return repository.updateConfirmedAt(
                code, LocalDateTime.now());
    }


}
