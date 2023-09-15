package be.technifutur.backend.models.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OfferForm {
    @NotBlank
    String offerTo;
    @NotNull
    Long gameId;
}
