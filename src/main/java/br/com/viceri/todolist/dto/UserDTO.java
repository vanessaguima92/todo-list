package br.com.viceri.todolist.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private String name;
    private String email;

    public UserDTO(String name, String email){
        this.name = name;
        this.email = email;
    }
}
