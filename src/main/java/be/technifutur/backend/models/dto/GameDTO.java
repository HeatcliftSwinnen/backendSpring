package be.technifutur.backend.models.dto;

import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.entity.Studio;
import be.technifutur.backend.models.enums.Tag;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class GameDTO {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private double price;
    private Integer coinPrice;
    private String description;
    private Studio studio;
    private Set<Tag> tags;

    public static GameDTO toDTO(Game entity){
        if(entity == null)
            return null;
        return GameDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .releaseDate(entity.getReleaseDate())
                .price(entity.getPrice())
                .coinPrice(entity.getCoinPrice())
                .description(entity.getDescription())
                .studio(entity.getStudio())
                .tags(entity.getTags())
                .build();
    }
}
