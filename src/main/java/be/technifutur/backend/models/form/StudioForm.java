package be.technifutur.backend.models.form;

import be.technifutur.backend.models.entity.Studio;
import be.technifutur.backend.validation.contraint.NameUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudioForm {

    @NotBlank
    @NameUnique
    @Size(min = 2,max = 50)
    private String name;

    public Studio toEntity(){
        Studio studio = new Studio();
        studio.setName( this.name );
        return studio;
    }

}