package br.com.viceri.todolist.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserDTO {
    private String name;
    private String email;
    private String password;
}
