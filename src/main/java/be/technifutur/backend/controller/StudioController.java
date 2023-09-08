package be.technifutur.backend.controller;


import be.technifutur.backend.models.dto.StudioDTO;
import be.technifutur.backend.models.form.StudioForm;
import be.technifutur.backend.service.StudioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studio")
public class StudioController {

    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    //create
    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody @Valid StudioForm form){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body( studioService.add(form.toEntity()));
    }
    //READ
    @GetMapping
    public ResponseEntity<List<StudioDTO>> getAll(){
        return ResponseEntity.ok(
                studioService.getAll().stream().map(StudioDTO::toDTO).toList()
        );
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<StudioDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(StudioDTO.toDTO( studioService.getOne(id)));
    }

    //UPDATE
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid StudioForm form){
        studioService.update(id,form.toEntity());
        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        studioService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
