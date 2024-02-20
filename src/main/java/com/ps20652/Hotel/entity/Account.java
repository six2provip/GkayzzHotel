package com.ps20652.Hotel.entity;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")

@Entity
@Table(name = "accounts")
@Data
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_authenticated")
    private Boolean isAuthenticated = false;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Customer> customers;

   public Collection<? extends GrantedAuthority> getAuthorities(String role) {
         return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Enumerated(EnumType.STRING)
    private Provider provider;
 
    public Provider getProvider() {
        return provider;
    }
 
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
