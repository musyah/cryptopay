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
    @Column(nullable = false,unique = true)
    private String address;

    @Column(columnDefinition="mediumtext")
    private String pin;

    @Column(precision = 20, scale = 8)
    private double balance;

    public Wallet(UserInfo userInfo, String address, String pin, double balance) {
        this.userInfo = userInfo;
        this.address = address;
        this.pin = pin;
        this.balance = balance;
    }

}