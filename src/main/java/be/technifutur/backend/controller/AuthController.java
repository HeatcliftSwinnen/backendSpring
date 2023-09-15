package be.technifutur.backend.controller;

import be.technifutur.backend.models.dto.AuthDTO;
import be.technifutur.backend.models.entity.User;
import be.technifutur.backend.models.form.LoginForm;
import be.technifutur.backend.models.form.UserForm;
import be.technifutur.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private  final PasswordEncoder encoder;

    public AuthController(
            UserService userService,
            UserDetailsService userDetailsService,
            PasswordEncoder encoder) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    // POST - /auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserForm form){
        User user = form.toEntity();
        userService.register(user);
        return ResponseEntity.status( HttpStatus.CREATED )
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm form){
        String token = userService.login( form.getUsername(), form.getPassword() );
        User user = (User) userDetailsService. loadUserByUsername( form.getUsername() );
        return ResponseEntity.ok( AuthDTO.toDTO(token, user) );
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Authentication auth){
        String username = auth.getName();
        try {
            userService.changePassword(username, oldPassword, newPassword);
            return ResponseEntity.ok("Mot de passe changé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}