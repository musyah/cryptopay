package com.Cryptopay.Repository;

import com.Cryptopay.Entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional()
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    Optional<ConfirmationCode> findByCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationCode c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.code = ?1")
    int updateConfirmedAt(String code,
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
