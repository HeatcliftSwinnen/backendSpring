package be.technifutur.backend.models.entity;

import be.technifutur.backend.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id" , nullable = false,unique = true)
    private Long id;

    @Column(name = "user_firstName" , nullable = false)
    private String firstName;

    @Column(name = "user_lastName" , nullable = false)
    private String lastName;

    @Column(name = "user_birthDate" , nullable = false)
    private LocalDate birthDate;

    @Column(name = "user_phoneNumber" , nullable = false ,unique = true)
    private String phoneNumber;

    @Column(name = "user_username" , nullable = false)
    private String username;

    @Column(name = "user_email" ,nullable = false, unique = true)
    private String email;

    @Column(name = "user_password",nullable = false)
    private String password;

    @Column(name = "user_moneyBalance" , nullable = false)
    private Double moneyBalance =0.00;

    @Column(name = "user_coinBalance" , nullable = false)
    private Integer coinBalance =0;

    @Enumerated(EnumType.STRING)
    private Role role =Role.USER;

    @ManyToMany
    private Set<Game> library = new HashSet<>();

    @ManyToMany
    private Set<Game> wishList=new HashSet<>();

    @ManyToMany
    private Set<User> friendList=new HashSet<>();

    @OneToMany(mappedBy = "borrower")
    private Set<LoanRequest> pendingLoans = new HashSet<>();


    @OneToMany(mappedBy = "loaner")
    private Set<LoanRequest> activeLoans = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority( "ROLE_"+role.toString() )
        );
    }

    @Override
    public String getUsername() {
        return this.username;
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
