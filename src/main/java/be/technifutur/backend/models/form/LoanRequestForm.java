package be.technifutur.backend.models.form;

import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.entity.LoanRequest;
import be.technifutur.backend.models.entity.User;
import lombok.Data;


@Data
public class LoanRequestForm {

    private User loaner;
    private User borrower;
    private Game game;

    public LoanRequest toEntity(){
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setLoaner(this.loaner);
        loanRequest.setBorrower(this.borrower);
        loanRequest.setGame(this.game);
        return loanRequest;
    }
}
