package be.technifutur.backend.controller;

import be.technifutur.backend.models.dto.SmallGameDTO;
import be.technifutur.backend.models.dto.SmallUserDTO;
import be.technifutur.backend.models.dto.UserDTO;
import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.enums.Role;
import be.technifutur.backend.models.form.OfferForm;
import be.technifutur.backend.models.form.UserForm;
import be.technifutur.backend.service.GameService;
import be.technifutur.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final GameService gameService;

    public UserController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    //READ
    @GetMapping
    public ResponseEntity<List<SmallUserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(SmallUserDTO::toDTO).toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(UserDTO.toDTO(userService.getOne(id)));
    }

    //UPDATE
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserForm form) {
        userService.update(id, form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/money")
    public ResponseEntity<?> updateMoney(@PathVariable Long id, @RequestBody double money) {
        userService.updateMoney(id, money);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/coin")
    public ResponseEntity<?> updateCoin(@PathVariable Long id, @RequestBody Integer coin) {
        userService.updateCoin(id, coin);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id:[0-9]+}/role")
    public ResponseEntity<?> updateCredidentials(@PathVariable Long id, @RequestBody Role role) {
        userService.upgradeCredidentials(id, role);
        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/wishList")
    public ResponseEntity<Set<SmallGameDTO>> getWishList(Authentication auth) {
        String username = auth.getName();
        Set<SmallGameDTO> body = userService.getWishList(username).stream().map(SmallGameDTO::toDTO).collect(Collectors.toSet());
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/wishlist/delete")
    public ResponseEntity<?> deleteGameToWishList(Authentication auth, @RequestParam Long gameId) {
        String username = auth.getName();
        Game game = gameService.getOne(gameId);
        userService.deleteToWishList(username, game);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/wishlist/add")
    public ResponseEntity<?> addGameToWishlist(Authentication auth, @RequestParam Long gameId) {
        String username = auth.getName();
        Game game = gameService.getOne(gameId);
        userService.addToWishList(username, game);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/library/buy")
    public ResponseEntity<?> buyGame(Authentication auth, @RequestParam Long gameId) {
        String username = auth.getName();
        Game game = gameService.getOne(gameId);
        userService.buyGame(username, game);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/library/offer")
    public ResponseEntity<?> offerGame(Authentication auth, @RequestBody OfferForm form) {
        String username = auth.getName();
        Game game = gameService.getOne(form.getGameId());
        userService.offerGame(username, form.getOfferTo(), game);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/friendlist/add")
    public ResponseEntity<?> addToFriends(Authentication auth, @RequestParam String friendname) {
        String username = auth.getName();
        userService.addFriend(username, friendname);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/friendlist/delete")
    public ResponseEntity<?> removeToFriends(Authentication auth, @RequestParam String friendname) {
        String username = auth.getName();
        userService.removeFriend(username, friendname);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/friend")
    public ResponseEntity<Set<SmallUserDTO>> getFriendList(Authentication auth) {
        String username = auth.getName();
        Set<SmallUserDTO> body = userService.getFriendList(username).stream().map(SmallUserDTO::toDTO).collect(Collectors.toSet());
        return ResponseEntity.ok(body);
    }
}
