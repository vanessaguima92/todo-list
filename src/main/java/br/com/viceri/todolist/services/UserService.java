package br.com.viceri.todolist.services;

import br.com.viceri.todolist.dto.CreateUserDTO;
import br.com.viceri.todolist.dto.UserDTO;
import br.com.viceri.todolist.entity.User;
import br.com.viceri.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO create(CreateUserDTO newUser){
        this.userRepository.add(new User(newUser.getName(), newUser.getEmail()));
        return new UserDTO(newUser.getName(), newUser.getEmail());
    };
}