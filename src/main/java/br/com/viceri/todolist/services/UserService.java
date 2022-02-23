package br.com.viceri.todolist.services;

import br.com.viceri.todolist.dto.CreateUserDTO;
import br.com.viceri.todolist.entity.User;
import br.com.viceri.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User create(CreateUserDTO newUser){
        User user = new User();
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        final User savedUser = this.userRepository.save(user);

        return savedUser;
    };

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}