package be.technifutur.backend.models.dto;

import be.technifutur.backend.models.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmallUserDTO {
    private Long id;
    private String pseudo;


    public static SmallUserDTO toDTO(User entity){
        if(entity == null)
            return null;
        return SmallUserDTO.builder()
                .id(entity.getId())
                .pseudo(entity.getUsername())
                .build();
    }
}
