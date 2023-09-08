package be.technifutur.backend.validation.validators;

import be.technifutur.backend.service.GameService;
import be.technifutur.backend.validation.contraint.NameUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameUniqueGameValidator implements ConstraintValidator<NameUnique,String> {

    private final GameService gameService;

    public NameUniqueGameValidator(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !gameService.isNameTaken(name);
    }
}
