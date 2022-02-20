package br.com.viceri.todolist.controllers;

import br.com.viceri.todolist.dto.CreateUserDTO;
import br.com.viceri.todolist.dto.UserDTO;
import br.com.viceri.todolist.entity.User;
import br.com.viceri.todolist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUser(){
        List<User> users = this.userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping()
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody() CreateUserDTO createUserDTO){
        this.userService.create(createUserDTO);
        return ResponseEntity.ok(createUserDTO);
    }
}
