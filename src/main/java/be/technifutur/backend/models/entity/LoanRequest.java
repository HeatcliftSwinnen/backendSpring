package be.technifutur.backend.models.entity;

import be.technifutur.backend.models.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="loan_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loaner_user_id",nullable = false)
    private User loaner;

    @ManyToOne
    @JoinColumn(name = "borrower_user_id",nullable = false)
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "game_id",nullable = false)
    private Game game;

    @Column(name ="borrowed_date" ,nullable = false)
    private LocalDate borrowedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status = LoanStatus.PENDING;
}
