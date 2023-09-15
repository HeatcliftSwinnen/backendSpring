package be.technifutur.backend.service.impl;

import be.technifutur.backend.exceptions.MissMatchDataException;
import be.technifutur.backend.exceptions.NotEnoughResourceException;
import be.technifutur.backend.exceptions.ResourceNotFoundException;
import be.technifutur.backend.exceptions.UniqueViolationException;
import be.technifutur.backend.jwt.JWTUtils;
import be.technifutur.backend.models.entity.Game;
import be.technifutur.backend.models.entity.User;
import be.technifutur.backend.models.enums.Role;
import be.technifutur.backend.repository.UserRepository;
import be.technifutur.backend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private  final AuthenticationManager authenticationManager;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.encoder=encoder;
        this.authenticationManager = authenticationManager;
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
        if(user.getMoneyBalance()+money<0) {
            throw new NotEnoughResourceException("L'utilisateur avec l'id: " + id + " n'a pas les fond nécessaire " +
                    "pour " + "éffectuer cette opération");
        }
        user.setMoneyBalance(user.getMoneyBalance()+money);
        userRepository.save(user);
    }

    @Override
    public void updateCoin(Long id,Integer coin) {
        User user =getOne(id);
        if(user.getCoinBalance()+coin<0) {
            throw new NotEnoughResourceException("L'utilisateur avec l'id: " + id + " n'a pas les fond nécessaire " +
                    "pour " + "éffectuer cette opération");
        }
        user.setCoinBalance(user.getCoinBalance()+coin);
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

    @Override
    public void upgradeCredidentials(Long id,Role role) {
        User user = getOne(id);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void register(User user) {
        Assert.notNull(user, "user should not be null");

        if( userRepository.existsByUsername(user.getUsername()) )
            throw new UniqueViolationException("username");

        user.setPassword( encoder.encode(user.getPassword()) );
        userRepository.save(user);
    }

    @Override
    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username,
                password) );
        return JWTUtils.generateJwt( auth );
    }

    @Override
    public void addToWishList(String username, Game game) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        user.getWishList().add(game);
        userRepository.save(user);
    }

    @Override
    public void deleteToWishList(String username, Game game) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        Set<Game> wishList = user.getWishList();
        if(wishList.contains(game)){
            wishList.remove(game);
            user.setWishList(wishList);
            userRepository.save(user);
        }
        else {
            throw new ResourceNotFoundException("Le jeux n'est pas présent dans la wishList");
        }
    }

    @Override
    public Set<Game> getWishList(String username) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        return user.getWishList();
    }

    @Override
    public void buyGame(String username, Game game) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        if(user.getMoneyBalance()< game.getPrice()){
            throw new NotEnoughResourceException("not enough money");
        }
        user.getLibrary().add(game);
        user.getWishList().remove(game);
        userRepository.save(user);
    }

    @Override
    public void offerGame(String username,String friendname, Game game) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        User friend = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        if (user.getMoneyBalance()< game.getPrice()){
            throw new NotEnoughResourceException("not enough money");
        }
        friend.getLibrary().add(game);
        friend.getWishList().remove(game);
        userRepository.save(friend);
    }

    @Override
    public void changePassword(String username,String oldPassword, String newPassword) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new MissMatchDataException("Ancien mot de passe incorrect");
        }
        if (!newPassword.matches("^(?=.*[!=@#|$%^&*()_+{}\\[\\]:;<>,.?~\\-]).*(?=.*[A-Z]).*(?=.*[0-9]).*$")) {
            throw new MissMatchDataException("Le nouveau mot de passe doit contenir au moins 1 lettre majuscules , au" +
                    " moins un chiffre et au moins 1 caractère spéciale");
        }
        user.setPassword(encoder.encode(newPassword));
    }

    @Override
    public void addFriend(String username, String friendname) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        User friend = userRepository.findByUsername( friendname )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        if(username.equals(friendname)){
            throw new MissMatchDataException("Vous ne pouvez pas vous ajoutez vous même en tant que ami");
        }
        if(user.getFriendList().contains(friend)){
            throw new MissMatchDataException(friendname +" fait déja partie de votre liste d'amis");
        }
        else {
            user.getFriendList().add(friend);
            user = userRepository.save(user);
            friend.getFriendList().add(user);
            userRepository.save(friend);
        }
    }

    @Override
    public void removeFriend(String username, String friendname) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        User friend = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        user.getFriendList().remove(friend);
        friend.getFriendList().remove(user);
        userRepository.save(friend);
        userRepository.save(user);
    }

    @Override
    public Set<User> getFriendList(String username) {
        User user = userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException("user not found") );
        return user.getFriendList();
    }

}
