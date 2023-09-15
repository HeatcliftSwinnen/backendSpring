package be.technifutur.backend.service;

import be.technifutur.backend.models.entity.LoanRequest;
import be.technifutur.backend.models.enums.LoanStatus;

import java.util.List;

public interface LoanRequestService extends CrudService<LoanRequest,Long> {
    void updateStatus(Long id, LoanStatus newStatus);
    List<LoanRequest> getAllByStatus (LoanStatus status);
}
