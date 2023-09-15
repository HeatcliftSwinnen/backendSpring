package be.technifutur.backend.service.impl;

import be.technifutur.backend.exceptions.NotEnoughResourceException;
import be.technifutur.backend.exceptions.ResourceNotFoundException;
import be.technifutur.backend.models.dto.SmallGameDTO;
import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.repository.GameRepository;
import be.technifutur.backend.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Long add(Game game) {
        game=gameRepository.save(game);
        return game.getId();
    }

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll().stream().toList();
    }

    @Override
    public Game getOne(Long id) {
        return gameRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Le jeux avec l' id : " +id+
                " n'a pas été trouvé"));
    }

    @Override
    public void update(Long id, Game game) {
        game.setId(id);
        gameRepository.save( game );
    }


    @Override
    public void delete(Long id) {
        Game game = getOne( id );
        gameRepository.delete( game );
    }

    @Override
    public void updateMoneyPrice(Long id, double price) {
        Game game = getOne(id);
        if(price <0){
            throw new NotEnoughResourceException("Le prix ne peut pas être négatif");
        }
        game.setPrice(price);
        gameRepository.save(game);
    }

    @Override
    public void updateCoinPrice(Long id, Integer coin) {
        Game game = getOne(id);
        if(coin <0){
            throw new NotEnoughResourceException("Le prix ne peut pas être négatif");
        }
        game.setCoinPrice(coin);
        gameRepository.save(game);
    }

    @Override
    public void updateImageUrl(Long id,String imageUrl) {
        Game game =getOne(id);
        game.setImageUrl(imageUrl);
        gameRepository.save(game);
    }

    @Override
    public boolean isNameTaken(String name) {
        return gameRepository.existsByName(name);
    }

}
