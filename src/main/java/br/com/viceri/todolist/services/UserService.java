package br.com.viceri.todolist.services;

import br.com.viceri.todolist.dto.CreateUserDTO;
import br.com.viceri.todolist.dto.UserDTO;
import br.com.viceri.todolist.entity.User;
import br.com.viceri.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO create(CreateUserDTO newUser){
        User user = new User();
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        this.userRepository.save(user);

        return new UserDTO(newUser.getName(), newUser.getEmail());
    };

    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}