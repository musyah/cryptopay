package com.Cryptopay.Controller;

import com.Cryptopay.Entity.Wallet;
import com.Cryptopay.Service.WalletService;
import com.Cryptopay.dtos.TransactionDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "CryptoApp/Wallet")
public class WalletController {
   @Autowired
   private final WalletService walletService;

    @GetMapping("/wallet")
    public List<Wallet> getUsers(){
        return walletService.getWallet();
    }

    @PostMapping("transfer")
    public void sendCoin(Wallet wallet, @RequestBody TransactionDto Send) {
        walletService.SendCoins(wallet, Send);
    }
}
