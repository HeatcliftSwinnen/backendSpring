package be.technifutur.backend.service;

import be.technifutur.backend.models.entity.User;


public interface UserService extends CrudService<User,Long>{

    void updateMoney(Long id,double money);
    void updateCoin(Long id,Integer coin);
    boolean isEmailTaken(String email);
    boolean isPhoneTaken(String phoneNumber);
}
