package br.com.viceri.todolist.repository;

import br.com.viceri.todolist.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    public User add(User user){
        return user;
    };
}
