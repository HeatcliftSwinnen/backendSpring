package be.technifutur.backend.service;

import be.technifutur.backend.models.dto.SmallGameDTO;
import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.entity.User;
import be.technifutur.backend.models.enums.Role;

import java.util.Set;


public interface UserService extends CrudService<User,Long>{

    void updateMoney(Long id,double money);
    void updateCoin(Long id,Integer coin);
    boolean isEmailTaken(String email);
    boolean isPhoneTaken(String phoneNumber);
    void upgradeCredidentials (Long id, Role role);
    void register(User User);
    String login(String username, String password);
    void addToWishList(String username, Game game);
    void deleteToWishList(String username,Game game);
    Set<Game> getWishList(String username);
    void buyGame(String username,Game game);
    void offerGame(String username,String friendname,Game game);
    void changePassword(String username,String oldPassword,String newPassword);
    void addFriend (String username,String friendname);
    void removeFriend (String username,String friendname);
    Set<User> getFriendList(String username);
}
