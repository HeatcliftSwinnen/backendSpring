package be.technifutur.backend.repository;

import be.technifutur.backend.models.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {
    boolean existsByName(String name);
}
