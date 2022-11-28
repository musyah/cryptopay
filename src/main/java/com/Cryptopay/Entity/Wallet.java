package com.Cryptopay.Entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallet")

public class Wallet{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long Id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            name = "User_Deets"
    )
    private UserInfo userInfo;
    @Column(nullable = false,columnDefinition ="mediumtext")
    private String address;

    @Column(columnDefinition ="mediumtext")
    private String fileName;

    @Column(columnDefinition="mediumtext")
    private String password;

    @Column(columnDefinition="mediumtext")
    private String passwordKey;

//    @DecimalMin("0")
    @Column(precision = 20, scale = 8)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CoinType coinType;


}