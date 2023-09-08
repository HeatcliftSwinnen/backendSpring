package be.technifutur.backend.validation.validators;

import be.technifutur.backend.service.UserService;
import be.technifutur.backend.validation.contraint.EmailUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailUniqueValidator implements ConstraintValidator<EmailUnique,String> {

    private final UserService userService;

    public EmailUniqueValidator(UserService userService) {
        this.userService = userService;
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userService.isEmailTaken(email);
    }
}
