package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.data_model.PasswordChangeDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.SignupDTO;
import edu.sabanciuniv.cs308.ecommerce.data_model.UserIdDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.userUpdateDto;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.FindUsernameException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.LoginException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.SignupException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.VerificationException;
import edu.sabanciuniv.cs308.ecommerce.login_system.LoginDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {


    @Autowired
    UserRepository repo;

    public User signupUser(SignupDTO user) throws SignupException {
        if(repo.findByEmailOrUsername(user.getEmail(),user.getUsername()) != null){
            throw new SignupException("User already exists!");
        } else if (user.getUsername().length() < 3 || user.getUsername().length() > 10 || user.getPassword().length() < 8 || user.getPassword().length() > 20) {
            throw new SignupException("Length of Username has to be between 3-10\n" +
                    "Length of Password has to be between 8-20"); }
        else if(user.getFirstName().length() == 0 || user.getLastName().length() == 0)
            throw new SignupException("Please enter your name and surname.");
        else if(user.getEmail().length() == 0)
            throw new SignupException("Please enter a valid email address.");


        else {
            User signedupUser = new User();
            signedupUser.setFirstName(user.getFirstName());
            signedupUser.setLastName(user.getLastName());
            signedupUser.setEmail(user.getEmail());
            signedupUser.setPassword(user.getPassword());
            signedupUser.setUsername(user.getUsername());
            signedupUser.setUserRole(user.getUserRole());
            signedupUser.setActive(true);
            signedupUser.setVerified(false);

            return repo.save(signedupUser);
        }
    }

        public User loginUser(LoginDto loginDto) throws LoginException {

        User user = repo.findByEmailAndPasswordAndActiveTrue(loginDto.getEmailOrUsername(),loginDto.getPassword());
        if(user == null){
            User user2 = repo.findByUsernameAndPasswordAndActiveTrue(loginDto.getEmailOrUsername(),loginDto.getPassword());
            if(user2 == null) {
                throw new LoginException("Invalid login!");
            }
            return user2;
        }
        return user;
    }


        public User updateUser(userUpdateDto dto) throws LoginException {
        User user1 = repo.findUserById(dto.getUserId());
        if(user1 == null){
            throw new LoginException("Invalid user!");
        }
        user1.setFirstName(dto.getFirstName());
        user1.setLastName(dto.getLastName());
        user1.setUsername(dto.getUsername());
        user1.setEmail(dto.getEmail());


        return repo.save(user1);

    }

    public User updateUserPassword(PasswordChangeDto dto) throws LoginException {
        User user1 = repo.findUserById(dto.getUserId());
        if(user1 == null){
            throw new LoginException("Invalid user!");
        }
        if(user1.getPassword().equals(dto.getCurrentPassword())){
            if(dto.getNewPassword().equals(dto.getReTypePassword())){
                user1.setPassword(dto.getNewPassword());
            }
            else{
                throw new LoginException("Please retype your new password correctly!");
            }
        }
        else{
            throw new LoginException("Please enter your current password correctly!");
        }


        return repo.save(user1);

    }


    public User findUserByUsername(String username) throws FindUsernameException {
        User user = repo.findByUsername(username);
        if(user == null) {
            throw new FindUsernameException("Could not found user");
        }
        return user;
    }

    public boolean isUserVerified(Long id) throws FindUsernameException {
        User user = repo.findUserById(id);
        if(user == null) {
            throw new FindUsernameException("Could not found user");
        }
        return user.isVerified();
    }

    public Iterable<User> getUsers(){
        return repo.findAll();
    }
}
