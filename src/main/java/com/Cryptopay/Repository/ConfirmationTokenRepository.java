package com.Cryptopay.Repository;

import com.Cryptopay.Entity.ConfirmationToken;
import com.Cryptopay.Entity.UserInfo;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional()
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

//
//    @Transactional
//    @Query("Select from ConfirmationToken c" +
//            "Where c.userinfo.email = ?2"
//             )
//    String getToken();
//
//    @Transactional
//    @Query("Select from ConfirmationToken c" +
//            "Where c.userinfo.email = ?2"+
//            "get c.token"
//    )
//    String getToken();
}
