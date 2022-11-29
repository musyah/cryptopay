package com.Cryptopay.Service;

import com.Cryptopay.Entity.Transactions;
import com.Cryptopay.Entity.Wallet;
import com.Cryptopay.Repository.TransactionRepository;
import com.Cryptopay.Repository.WalletRepository;
import com.Cryptopay.dtos.TransactionDto;
import lombok.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class WalletService {
    @Autowired
    private WalletRepository repo;
    @Autowired
    private final TransactionRepository transactionRepo;
    private final static Logger LOGGER = LoggerFactory.getLogger(WalletService.class);


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
    public int TransactionIdGenerator(){
        Random rand = new Random();
        int maxValues= 99999999;
        return rand.nextInt(maxValues);
    };
    @PostConstruct
    public void SendCoins(Wallet wallet,TransactionDto Send) throws Exception {

        String trId = String.valueOf(TransactionIdGenerator());
        Transactions transactions = new Transactions(
                "SendId: "+trId,
                LocalDateTime.now(),
                "to: "+Send.getSubject(),
                Send.getAmount(),
                wallet
        );
        String Address = Send.getSubject();
        if (repo.findByAddress(Address)!=null) {
            try {
                double prevBalance = wallet.getBalance();
                double newBalance = prevBalance-(Send.getAmount());
                wallet.setBalance(newBalance);
                LOGGER.info("Sending Amount ", Send.getAmount(), " to ", transactions.getSubject());
                LOGGER.info("Sending....");
                transactionRepo.save(transactions);

                String from=wallet.getAddress();
                String tId = String.valueOf(TransactionIdGenerator());
                double AmountReceived = Send.getAmount();
                Transactions transaction = new Transactions(
                        "ReceiverId: "+tId,
                        LocalDateTime.now(),
                        "from: "+from,
                        AmountReceived,
                        repo.findByAddress(Address).getClass().newInstance()
                );

                try {
                    double PrevBalance = repo.findByAddress(Address).getBalance();
                    double NewBalance = PrevBalance+(Send.getAmount());
                    repo.findByAddress(Address).setBalance(NewBalance);
                    LOGGER.info("Received Amount ", Send.getAmount(), " from ", Send.getSubject());
                    LOGGER.info("Sending....");
                    transactionRepo.save(transaction);

                } catch (Exception e) {
                    throw new Exception("Balance not Updated");
                }
            } catch (Exception e) {
                throw new Exception("insufficient funds");
            }
        }
        else {
            throw new IllegalStateException("User with the wallet Address could not be found");
        }


    }

//    @PostConstruct
//    public void start() {
//        walletAppKit.startAsync();
//        walletAppKit.awaitRunning();
//
//        walletAppKit.wallet().addCoinsReceivedEventListener(
//                (wallet, tx, prevBalance, newBalance) -> {
//                    Coin value = tx.getValueSentToMe(wallet);
//                    System.out.println("Received tx for " + value.toFriendlyString());
//                    Futures.addCallback(tx.getConfidence().getDepthFuture(1),
//                            new FutureCallback<TransactionConfidence>() {
//                                @Override
//                                public void onSuccess(TransactionConfidence result) {
//                                    System.out.println("Received tx " +
//                                            value.toFriendlyString() + " is confirmed. ");
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//                                }
//                            }, MoreExecutors.directExecutor());
//                });
//
//        Address sendToAddress = LegacyAddress.fromKey(networkParameters, walletAppKit.wallet().currentReceiveKey());
//        System.out.println("Send coins to: " + sendToAddress);
//
//    }
}
