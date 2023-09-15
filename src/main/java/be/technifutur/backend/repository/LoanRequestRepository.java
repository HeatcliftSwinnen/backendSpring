package be.technifutur.backend.repository;

import be.technifutur.backend.models.entity.LoanRequest;
import be.technifutur.backend.models.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {

    List<LoanRequest> findByStatus(LoanStatus status);

}
