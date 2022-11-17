package com.Cryptopay.Entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")

public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long Id;
    @Column(nullable = false, unique = true, name = "WalletId")
    private Integer WalletId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            name = "User_Deets"
    )
    private UserInfo userInfo;
    @Column(nullable = false, unique = true, name = "Balance")
    private Double Balance;
    @Column(nullable = false, name = "pin")
    private String pin;

    public Wallet(Integer walletId, String name, Double balance, String pin, UserInfo userInfo) {
        WalletId = walletId;
        this.userInfo = userInfo;
        Balance = balance;
        this.pin = pin;

    }
}