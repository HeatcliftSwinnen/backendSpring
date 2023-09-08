package be.technifutur.backend.repository;

import be.technifutur.backend.models.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio,Long> {
    boolean existsByName(String name);
}
