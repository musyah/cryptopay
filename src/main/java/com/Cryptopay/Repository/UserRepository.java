package com.Cryptopay.Repository;

import com.Cryptopay.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public interface  UserRepository extends JpaRepository<UserInfo, Long> {

     UserInfo findByEmail(String email);

     @Transactional
     @Modifying
     @Query
             ("UPDATE UserInfo u " +
                     "SET u.enabled = TRUE WHERE u.email = ?1")
     int enableuserInfo(String email);

}