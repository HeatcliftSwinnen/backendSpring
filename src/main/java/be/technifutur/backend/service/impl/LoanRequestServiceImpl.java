package be.technifutur.backend.service.impl;

import be.technifutur.backend.exceptions.ResourceNotFoundException;
import be.technifutur.backend.models.entity.LoanRequest;
import be.technifutur.backend.models.enums.LoanStatus;
import be.technifutur.backend.repository.LoanRequestRepository;
import be.technifutur.backend.service.LoanRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;

    public LoanRequestServiceImpl(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    @Override
    public Long add(LoanRequest loanRequest) {
        loanRequest=loanRequestRepository.save(loanRequest);
        return  loanRequest.getId();
    }

    @Override
    public List<LoanRequest> getAll() {
        return loanRequestRepository.findAll().stream().toList();
    }

    @Override
    public List<LoanRequest> getAllByStatus(LoanStatus status) {
        return loanRequestRepository.findByStatus(status);
    }
    @Override
    public LoanRequest getOne(Long id) {
        return loanRequestRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("La location avec l'" +
                " id : " +id+
                " n'a pas été trouvé"));
    }
    @Override
    public void update(Long id, LoanRequest loanRequest) {
        loanRequest.setId(id);
        loanRequestRepository.save(loanRequest);
    }


    @Override
    public void updateStatus(Long id, LoanStatus newStatus) {
        LoanRequest loanRequest = getOne(id);
        loanRequest.setStatus(newStatus);
        loanRequestRepository.save(loanRequest);
    }


    @Override
    public void delete(Long id) {
        LoanRequest loanRequest =getOne(id);
        loanRequestRepository.delete(loanRequest);
    }
}
