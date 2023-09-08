package be.technifutur.backend.models.entity;

import be.technifutur.backend.models.enums.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="game_id", nullable = false, unique = true)
    private Long id;

    @Column(name="game_name", nullable = false,unique = true)
    private String name;

    @Column(name = "game_release",nullable = false)
    private LocalDate releaseDate;

    @Column(name = "game_price", nullable = false)
    private double price;

    @Column(name = "game_price", nullable = false)
    private Integer coinPrice;

    @Column(name = "game_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "game_studio_id", nullable = false)
    private Studio studio;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "game_tag",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
