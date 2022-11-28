package com.Cryptopay.Config;
import com.google.common.util.concurrent.AbstractIdleService;
import org.apache.http.protocol.HttpService;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.utils.BriefLogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class WalletConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(WalletConfig.class);
    @Value("${bitcoin.network}")
    private String network;
    @Value("${bitcoin.file-prefix}")
    private String filePrefix;
    @Value("${bitcoin.file-location}")
    private String btcFileLocation;
    private NetworkParameters networkParameters;
    public WalletConfig() {
        BriefLogFormatter.init();
    }


    @Value("${ethereum.service.url}")
    private String url;

//    @Bean
//    Web3j getEthereumCononnection() throws IOException {
//
//        String serverIp = url;
//        LOGGER.info("Ethereum sync serverIp : " + serverIp);
//        Web3j web3 = Web3j.build(new HttpService(serverIp));
//        LOGGER.info("connected to ethereum server 123" + web3);
//        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
//        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
//        LOGGER.info("Ethereum client version" + clientVersion);
//        return web3;
//
//    }

    @Bean
    public NetworkParameters networkParameters() {
        if(network.equals("testnet")) {
            return TestNet3Params.get();
        } else if(network.equals("regtest")) {
            return RegTestParams.get();
        } else {
            return MainNetParams.get();
        }
    }
//    @Bean
//    public AbstractIdleService walletKit(){
//        return new AbstractIdleService() {
//            @Override
//            protected void startUp() throws Exception {
//            }
//            @Override
//            protected void shutDown() throws Exception {
//            }
//        };
//    }

    @Bean
    public WalletAppKit walletAppKit(@Autowired NetworkParameters networkParameters) {
        return new WalletAppKit(networkParameters, new File(btcFileLocation), filePrefix) {
            @Override
            protected void onSetupCompleted() {
                if (wallet().getKeyChainGroupSize() < 1) {
                    wallet().importKey(new ECKey());
                }
            }
        };
    }

}

