package be.technifutur.backend.validation.contraint;

import jakarta.validation.Payload;

public @interface NameUnique {
    String message() default "Nom déja utilisé";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
