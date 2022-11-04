package com.Cryptopay.Controller;

import com.Cryptopay.Entity.Wallet;
import com.Cryptopay.Service.WalletService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "CryptoApp/Wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

//    @GetMapping("/wallet")
//    public List<Wallet> getUsers(){
//        return walletService.getWallet();
//    }

    @PostMapping("/setup")
    public String createWallet(@RequestBody Wallet wallet){
        return walletService.save(wallet);
    }
}
