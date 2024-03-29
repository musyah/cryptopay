package com.Cryptopay.Entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "userInfo")
@NoArgsConstructor
@EqualsAndHashCode

public class UserInfo implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long Id;
    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @Column(nullable = false, name = "first_name")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(name = "Cpassword",nullable = true)
    @Transient
    private String Cpassword;
    @Column(nullable = false, name = "mobile")
    private String mobile;
    private String UserRole = "user";
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "Wallet"
    )
    private Wallet wallet;
    private Boolean locked = false;
    private Boolean enabled = false;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCpassword(String cpassword) {
        Cpassword = cpassword;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getUserRole() {
        return UserRole;
    }
    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
    public Wallet getWallet() {
        return wallet;
    }
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    public Boolean getLocked() {
        return locked;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserRole);
        return Collections.singletonList(authority);
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getMobile() {
        return mobile;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UserInfo(String email, String firstName, String lastName, String password, String cpassword, String mobile, Wallet wallet) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        Cpassword = cpassword;
        this.mobile = mobile;
        this.wallet = wallet;
    }

    public UserInfo(String email, String firstName, String lastName, String password, String cpassword, String mobile) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        Cpassword = cpassword;
        this.mobile = mobile;
    }
}
