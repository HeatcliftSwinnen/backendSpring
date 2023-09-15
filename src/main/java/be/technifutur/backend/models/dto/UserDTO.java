package be.technifutur.backend.models.dto;

import be.technifutur.backend.models.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String username;
    private String email;
    private Double moneyBalance;
    private Integer coinBalance ;

    public static UserDTO toDTO(User entity){
        if(entity == null)
            return null;
        return UserDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .phoneNumber(entity.getPhoneNumber())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .moneyBalance(entity.getMoneyBalance())
                .coinBalance(entity.getCoinBalance())
                .build();
    }
}
