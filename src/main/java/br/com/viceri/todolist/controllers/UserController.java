package br.com.viceri.todolist.controllers;

import br.com.viceri.todolist.dto.CreateUserDTO;
import br.com.viceri.todolist.dto.UserDTO;
import br.com.viceri.todolist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserDTO> getUser(){
        var user = new UserDTO("John", "john@email.test");
        System.out.println(String.format("%s-%s", user.getEmail(), user.getName()));
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody() CreateUserDTO createUserDTO){
        this.userService.create(createUserDTO);
        return ResponseEntity.ok(createUserDTO);
    }
}
