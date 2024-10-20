package org.example.userstask.services;

import lombok.RequiredArgsConstructor;
import org.example.userstask.entity.User;
import org.example.userstask.utils.AESEncryptionDecryption;
import org.example.userstask.web.model.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class UserComponentImpl
 */
@RequiredArgsConstructor
@Component
public class UserComponentImpl implements UserComponent{
    @Autowired
    UserRepo repo;
    @Autowired
    AESEncryptionDecryption utils;

    private Optional<User> findById(Long id) {
        return this.repo.findById(id);
    }

    @Override
    public User findByIdOrDie(Long id) {
        User user = findById(id)
                .orElseThrow(() -> new RuntimeException("user with this id not found !"));
        user.setPassword(utils.decrypt(user.getPassword()));
        return user;
    }

    @Override
    public User commit(UserRequest request)  {
        if(repo.checkUserExistByName(request.getUsername())>0) throw new RuntimeException("user with such name is exist");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(utils.encrypt(request.getPassword()));

        if(!checkValidEmail(request.getEmail())){
            throw new RuntimeException("Email is not valid !");
        }

        user.setEmail(request.getEmail());
        LocalDateTime date = LocalDateTime.now();
        user.setCreatedAt(date);
        user.setUpdatedAt(date);
        User userFromTable = this.repo.save(user);
        String password = userFromTable.getPassword();

        try {
            userFromTable.setPassword(utils.decrypt(password));
        }catch(Exception e){
            e.printStackTrace();
        }
        return userFromTable;
    }

    @Override
    public List<User> findAll() {
        List<User> users = this.repo.findAll();

            users.forEach(u-> {
                try {
                    u.setPassword(utils.decrypt(u.getPassword()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        return users;
    }

    @Override
    public void deleteUserById(Long id){
        this.repo.deleteById(id);
    }

    @Override
    public void updateUserById(Long id, UserRequest request)  {
        this.repo.updateUserById(
                request.getUsername(),
                request.getEmail(),
                utils.encrypt(request.getPassword()),
                LocalDateTime.now(),
                id);
    }

    private boolean checkValidEmail(String email){return EmailValidator.getInstance().isValid(email);}
}
