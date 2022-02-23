package br.com.viceri.todolist.controllers;

import br.com.viceri.todolist.dto.CreateUserDTO;
import br.com.viceri.todolist.entity.User;
import br.com.viceri.todolist.services.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name="to-do-list")
public class UserController {

    private final UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve all users information successfully.", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = this.userService.getAll();
        return ResponseEntity.ok(users);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created user successfully.", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody() CreateUserDTO createUserDTO){
        final User user = this.userService.create(createUserDTO);
        return ResponseEntity.created(null).body(user);
    }
}
