package be.technifutur.backend.tasks;

import java.time.LocalDate;
import java.util.List;
import be.technifutur.backend.models.entity.LoanRequest;
import be.technifutur.backend.models.enums.LoanStatus;
import be.technifutur.backend.service.LoanRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@EnableAsync
public class LoanExpirationChecker {

    private final LoanRequestService loanRequestService;

    public LoanExpirationChecker(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void consumeLoans(){
        log.info("scheduled task [consumeLoans]");
        checkLoanExpirations();
    }

    private void checkLoanExpirations() {
        LocalDate now = LocalDate.now();
        // Récupérez tous les prêts en cours depuis la base de données
        List<LoanRequest> activeLoans = loanRequestService.getAllByStatus(LoanStatus.ACCEPTED);

        for (LoanRequest loan : activeLoans) {
            LocalDate borrowedDate = loan.getBorrowedDate();
            LocalDate expirationDate = borrowedDate.plusDays(1);

            if (now.isAfter(expirationDate)) {
                // Le prêt a expiré, mettez à jour le statut du prêt
                loan.setStatus(LoanStatus.CONSUMED);
                loanRequestService.updateStatus(loan.getId(), LoanStatus.CONSUMED);
            }
        }
    }
}