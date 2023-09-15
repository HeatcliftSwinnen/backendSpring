package be.technifutur.backend.controller;

import be.technifutur.backend.models.dto.LoanRequestDTO;
import be.technifutur.backend.models.enums.LoanStatus;
import be.technifutur.backend.models.form.LoanRequestForm;
import be.technifutur.backend.service.LoanRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loanRequest")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @PostMapping
    public ResponseEntity<Long> addGame(@RequestBody @Valid LoanRequestForm form){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanRequestService.add(form.toEntity()));
    }

    @GetMapping
    public ResponseEntity<List<LoanRequestDTO>> getAll(){
        return ResponseEntity.ok(loanRequestService.getAll().stream().map(LoanRequestDTO::toDTO).toList());
    }
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<LoanRequestDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(LoanRequestDTO.toDTO(loanRequestService.getOne(id)));
    }
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid LoanRequestForm form){
        loanRequestService.update(id,form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/status")
    public ResponseEntity<?> updateMoney(@PathVariable Long id, @RequestBody LoanStatus newStatus){
        loanRequestService.updateStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        loanRequestService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
