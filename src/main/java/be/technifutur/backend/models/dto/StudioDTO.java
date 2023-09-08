package be.technifutur.backend.models.dto;

import be.technifutur.backend.models.entity.Studio;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class StudioDTO {
    private Long id;
    private String name;
    private Set<SmallGameDTO> games;

    public static StudioDTO toDTO(Studio entity){
        if(entity == null)
            return null;
        return StudioDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .games(entity.getGames().stream().map(SmallGameDTO::toDTO).collect(Collectors.toSet()))
                .build();
    }
}
