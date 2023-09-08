package be.technifutur.backend.models.dto;

import be.technifutur.backend.models.entity.Game;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SmallGameDTO {
    private Long id;
    private String name;

    public static SmallGameDTO toDTO (Game entity){
        if(entity==null)
            return null;
        return SmallGameDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
