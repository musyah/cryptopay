//package com.Cryptopay.Service;
//
//import com.Cryptopay.Repository.WalletRepository;
//import com.google.common.util.concurrent.FutureCallback;
//import com.google.common.util.concurrent.Futures;
//import com.google.common.util.concurrent.MoreExecutors;
//import lombok.AllArgsConstructor;
//import org.bitcoinj.core.*;
//import org.bitcoinj.kits.WalletAppKit;
//import org.bitcoinj.wallet.SendRequest;
//import org.bitcoinj.wallet.Wallet;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.PostConstruct;
//@AllArgsConstructor
//public class BitWalletService {
//    @Autowired
//    private WalletRepository repo;
//    @Autowired
//    private final WalletAppKit walletAppKit;
//    @Autowired
//    private NetworkParameters networkParameters;
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
//                                public void onFailure(Throwable t) {}
//                            }, MoreExecutors.directExecutor());
//                });
//
//        Address sendToAddress = LegacyAddress.fromKey(networkParameters, walletAppKit.wallet().currentReceiveKey());
//        System.out.println("Send coins to: " + sendToAddress);
//
//    }
//
//    public void send(String value, String to) {
//        try {
//            Address toAddress = LegacyAddress.fromBase58(networkParameters, to);
//            SendRequest sendRequest = SendRequest.to(toAddress, Coin.parseCoin(value));
//            sendRequest.feePerKb = Coin.parseCoin("0.0005");
//            Wallet.SendResult sendResult = walletAppKit.wallet().sendCoins(walletAppKit.peerGroup(), sendRequest);
//            sendResult.broadcastComplete.addListener(() ->
//                            System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getTxId()),
//                    MoreExecutors.directExecutor());
//        } catch (InsufficientMoneyException e) {
//            throw  new RuntimeException(e);
//        }
//    }
//}
