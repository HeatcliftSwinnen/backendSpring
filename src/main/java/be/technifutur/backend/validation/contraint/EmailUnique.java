package be.technifutur.backend.validation.contraint;

import jakarta.validation.Payload;

public @interface EmailUnique {
    String message() default "Numéro de téléphone déja utilisé";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
