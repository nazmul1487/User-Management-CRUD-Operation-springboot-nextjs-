package com.example.UserManagement.service;

import com.example.UserManagement.exception.UserAlreadyExistsException;
import com.example.UserManagement.exception.UserNotFoundException;
import com.example.UserManagement.model.User;
import com.example.UserManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        if (userAlreadyExists(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail()+ "already exits");
        }

        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user, Long id) {
        return userRepository.findById(id).map(ui -> {
            ui.setFirstName(user.getFirstName());
            ui.setLastName(user.getLastName());
            ui.setEmail(user.getEmail());
            ui.setContactNo(user.getContactNo());

            return userRepository.save(ui);
        }).orElseThrow(()-> new UserNotFoundException("The User could not found."));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The User could not found with ID:" + id));
    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)){
            throw new UserNotFoundException("The user could not found with ID:" + id);
        }
        userRepository.deleteById(id);
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
