package ru.serji.kuklyasha.security.jwt;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.User;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmailIgnoreCase(email).orElseThrow(
                () -> new UsernameNotFoundException("User '" + email + "' was not found"));
        return new AuthUser(user);
    }
}
