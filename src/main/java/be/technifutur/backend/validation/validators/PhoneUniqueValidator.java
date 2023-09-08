package be.technifutur.backend.validation.validators;

import be.technifutur.backend.service.UserService;
import be.technifutur.backend.validation.contraint.PhoneUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneUniqueValidator implements ConstraintValidator<PhoneUnique,String> {

    private final UserService userService;

    public PhoneUniqueValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext Context) {
        return !userService.isPhoneTaken(phoneNumber);
    }
}
