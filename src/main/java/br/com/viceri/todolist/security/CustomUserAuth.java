package br.com.viceri.todolist.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUserAuth extends User {
    private UUID userId;

    public <E> CustomUserAuth(String email, String password, Collection<? extends GrantedAuthority> authorities, UUID userId) {
        super(email, password, authorities);
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId){
        this.userId = userId;
    }
}
