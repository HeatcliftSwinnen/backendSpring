package be.technifutur.backend.validation.validators;

import be.technifutur.backend.service.StudioService;
import be.technifutur.backend.validation.contraint.NameUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameUniqueStudioValidator implements ConstraintValidator<NameUnique,String> {

    private final StudioService studioService;

    public NameUniqueStudioValidator(StudioService studioService) {
        this.studioService = studioService;

    }
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !studioService.isNameTaken(name);
    }

}
