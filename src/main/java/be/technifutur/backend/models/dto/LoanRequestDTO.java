package be.technifutur.backend.models.dto;

import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.entity.LoanRequest;
import be.technifutur.backend.models.entity.User;
import be.technifutur.backend.models.enums.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanRequestDTO {
    private Long id;
    private User loaner;
    private User borrower;
    private Game game;
    private LocalDate borrowedDate;
    private LoanStatus status;

    public static LoanRequestDTO toDTO(LoanRequest entity){
        if(entity == null)
            return null;
        return LoanRequestDTO.builder()
                .id(entity.getId())
                .loaner(entity.getLoaner())
                .borrower(entity.getBorrower())
                .game(entity.getGame())
                .borrowedDate(entity.getBorrowedDate())
                .status(entity.getStatus())
                .build();
    }
}
