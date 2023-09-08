package be.technifutur.backend.validation.contraint;

import jakarta.validation.Payload;

public @interface PhoneUnique {
    String message() default "email ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
