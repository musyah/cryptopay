package com.Cryptopay.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class ConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            nullable = false,
            name = "userId"
    )
    private UserInfo userInfo;

    public ConfirmationCode(String code, LocalDateTime createdAt, LocalDateTime expiresAt, UserInfo userInfo) {
        this.code = code;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.userInfo = userInfo;
    }


}
