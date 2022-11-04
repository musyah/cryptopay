package com.Cryptopay.Repository;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {



}
