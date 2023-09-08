package be.technifutur.backend.service.impl;

import be.technifutur.backend.exceptions.NotEnoughResourceException;
import be.technifutur.backend.exceptions.ResourceNotFoundException;
import be.technifutur.backend.models.entity.User;
import be.technifutur.backend.repository.UserRepository;
import be.technifutur.backend.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long add(User user) {
        user = userRepository.save(user);
        return user.getId();
    }

    @Override
    public List<User> getAll() {
       return userRepository.findAll().stream().toList();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("L'utilisateur avec l' id " +
                ": " +id+
                " n'a pas été trouvé"));
    }

    @Override
    public void update(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }


    @Override
    public void updateMoney(Long id,double money) {
        User user =getOne(id);
        if(user.getMoneyBalance()-money<0) {
            throw new NotEnoughResourceException("L'utilisateur avec l'id: " + id + " n'a pas les fond nécessaire " +
                    "pour " + "éffectuer cette opération");
        }
        user.setMoneyBalance(user.getMoneyBalance()-money);
        userRepository.save(user);
    }

    @Override
    public void updateCoin(Long id,Integer coin) {
        User user =getOne(id);
        if(user.getCoinBalance()-coin<0) {
            throw new NotEnoughResourceException("L'utilisateur avec l'id: " + id + " n'a pas les fond nécessaire " +
                    "pour " + "éffectuer cette opération");
        }
        user.setCoinBalance(user.getCoinBalance()-coin);
        userRepository.save(user);
    }
    @Override
    public void delete(Long id) {
        User user = getOne(id);
        userRepository.delete(user);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isPhoneTaken(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}
