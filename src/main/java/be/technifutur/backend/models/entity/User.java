package be.technifutur.backend.models.entity;

import be.technifutur.backend.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
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

    @Column(name = "user_pseudo" , nullable = false)
    private String pseudo;

    @Column(name = "user_email" ,nullable = false, unique = true)
    private String email;

    @Column(name = "user_password",nullable = false)
    private String password;

    @Column(name = "user_moneyBalance" , nullable = false)
    private Double moneyBalance =0.00;

    @Column(name = "user_coinBalance" , nullable = false)
    private Integer coinBalance =0;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private Set<Game> library = new HashSet<>();

    @ManyToMany
    private Set<Game> wishList=new HashSet<>();
}
