package be.technifutur.backend.service;

import be.technifutur.backend.models.dto.SmallGameDTO;
import be.technifutur.backend.models.entity.Game;

import java.util.List;

public interface GameService extends CrudService<Game,Long>{
    void updateMoneyPrice(Long id,double price);
    void updateCoinPrice(Long id,Integer coin);
    void updateImageUrl(Long id,String imageUrl);
    boolean isNameTaken(String name);
}
