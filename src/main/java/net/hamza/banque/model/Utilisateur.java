package net.hamza.banque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String identifiant;
    private String password;
    @Column(nullable = true)
    private Integer otp;

    public void setOtpNull() {
        this.otp = null;
    }

    @Enumerated(EnumType.STRING)
    @JsonSerialize
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    private Boolean estActif;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Compte> comptes = new ArrayList<>();

    @OneToMany( fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }



    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}