package br.com.viceri.todolist.security;

import br.com.viceri.todolist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<br.com.viceri.todolist.entity.User> user = this.userService.findByEmail(username);

        if (user.isPresent()) {
            return new CustomUserAuth(user.get().getEmail(), user.get().getPassword(),
                    new ArrayList<>(), user.get().getId());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
