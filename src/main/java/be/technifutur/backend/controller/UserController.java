package be.technifutur.backend.controller;

import be.technifutur.backend.models.dto.SmallUserDTO;
import be.technifutur.backend.models.dto.UserDTO;
import be.technifutur.backend.models.form.UserForm;
import be.technifutur.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create
    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody @Valid UserForm form){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.add(form.toEntity()));
    }

    //READ
    @GetMapping
    public ResponseEntity<List<SmallUserDTO>> getAll(){
        return ResponseEntity.ok(
                userService.getAll().stream().map(SmallUserDTO::toDTO).toList()
        );
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(UserDTO.toDTO(userService.getOne(id)));
    }

    //UPDATE
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid UserForm form){
        userService.update(id,form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/money")
    public ResponseEntity<?>updateMoney(@PathVariable Long id,@RequestBody double money){
        userService.updateMoney(id,money);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/coin")
    public ResponseEntity<?>updateCoin(@PathVariable Long id,@RequestBody Integer coin){
        userService.updateCoin(id,coin);
        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
