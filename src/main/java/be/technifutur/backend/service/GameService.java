package be.technifutur.backend.service;

import be.technifutur.backend.models.entity.Game;

public interface GameService extends CrudService<Game,Long>{
    void updateMoneyPrice(Long id,double price);
    void updateCoinPrice(Long id,Integer coin);
    boolean isNameTaken(String name);
}
