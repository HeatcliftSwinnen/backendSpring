package be.technifutur.backend.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id", nullable = false,unique = true)
    private Long id;

    @Column(name = "studio_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "studio")
    private Set<Game> games;
}
