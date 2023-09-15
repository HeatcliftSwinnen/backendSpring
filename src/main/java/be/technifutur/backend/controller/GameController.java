package be.technifutur.backend.controller;

import be.technifutur.backend.models.dto.GameDTO;
import be.technifutur.backend.models.dto.SmallGameDTO;
import be.technifutur.backend.models.form.GameForm;
import be.technifutur.backend.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Long> addGame(@RequestBody @Valid GameForm form){
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.add(form.toEntity()));
    }

    @GetMapping
    public ResponseEntity<List<SmallGameDTO>> getAll(){
        return ResponseEntity.ok(gameService.getAll().stream().map(SmallGameDTO::toDTO).toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<GameDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(GameDTO.toDTO(gameService.getOne(id)));
    }

    //UPDATE
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid GameForm form){
        gameService.update(id,form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/money")
    public ResponseEntity<?> updateMoney(@PathVariable Long id,@RequestBody double price){
        gameService.updateMoneyPrice(id, price);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/coin")
    public ResponseEntity<?> updateCoin(@PathVariable Long id,@RequestBody Integer coin){
        gameService.updateCoinPrice(id, coin);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id:[0-9]+}/imageUrl")
    public ResponseEntity<?> updateImageUrl(@PathVariable Long id,@RequestBody String imageUrl){
        gameService.updateImageUrl(id, imageUrl);
        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        gameService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

}
