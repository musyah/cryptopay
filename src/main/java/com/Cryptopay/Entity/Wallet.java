//package com.Cryptopay.Entity;
//
//import lombok.*;
//import javax.persistence.*;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "wallet")
//
//public class Wallet {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, unique = true, name = "Id")
//    private Long Id;
//    @Column(nullable = false, unique = true, name = "WalletId")
//    private Integer WalletId;
//    @Column(nullable = false, unique = true, name = "Name")
//    private String Name;
//    @Column(nullable = false, unique = true, name = "Balance")
//    private Double Balance;
//    @Column(nullable = false, name = "pin")
//    private String pin;
//    @OneToOne
//    @JoinColumn(
//            nullable = false,
//            name = "User_Deets"
//    )
//    private UserInfo userInfo;
//
//    public Wallet(Integer walletId, String name, Double balance, String pin, UserInfo userInfo) {
//        WalletId = walletId;
//        Name = name;
//        Balance = balance;
//        this.pin = pin;
//        this.userInfo = userInfo;
//    }
//}