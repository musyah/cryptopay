//package com.Cryptopay.Service;
//
//import com.Cryptopay.Entity.Wallet;
//import com.Cryptopay.Repository.WalletRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//
//public class WalletService {
//    @Autowired
//    private WalletRepository repo;
//
//    public List<Wallet> getWallet() {
//        return (List<Wallet>) repo.findAll();
//    }
//    public String save(Wallet wallet) {
//         repo.save(wallet);
//         return ("Wallet Created Successfully");
//        }
//}
