package com.Cryptopay.Repository;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Entity.Wallet;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("SELECT w FROM Wallet w WHERE w.address = ?1")
    Wallet findByAddress(String subject);

    @Query("SELECT w FROM Wallet w WHERE w.userInfo.email = ?1")
    UserInfo findByUserInfo(String email);
}
