package be.technifutur.backend.models.form;

import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.entity.Studio;
import be.technifutur.backend.models.enums.Tag;
import be.technifutur.backend.validation.contraint.NameUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

public class GameForm {

    @NotBlank
    @NameUnique
    @Size(min = 2,max = 50)
    private String name;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private double price;

    @NotNull
    private Integer coinPrice;

    @Size(max = 500)
    private String description;

    @NotNull
    private Studio studio;

    @Size(min = 1)
    private Set<Tag> tags;

    public Game toEntity(){
        Game game = new Game();
        game.setName(this.name);
        game.setReleaseDate(this.releaseDate);
        game.setPrice(this.price);
        game.setCoinPrice(this.coinPrice);
        game.setDescription(this.description);
        game.setStudio(this.studio);
        game.setTags(this.tags);
        return game;
    }
}
