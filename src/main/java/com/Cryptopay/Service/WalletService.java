package com.Cryptopay.Service;

import com.Cryptopay.Entity.Wallet;
import com.Cryptopay.Repository.WalletRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor

public class WalletService {
    @Autowired
    private WalletRepository repo;

    public List<Wallet> getWallet() {
        return (List<Wallet>) repo.findAll();
    }

    public String recharge(Wallet wallet) {
        return "Succesful";
    }

    public String save(Wallet wallet) {
        repo.save(wallet);
        return ("Wallet SET UP Successfully");
    }
}




