package ru.serji.kuklyasha.web;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.security.exception.*;
import ru.serji.kuklyasha.security.jwt.*;

import javax.validation.*;

@RestController
@RequestMapping(value = AuthController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@CrossOrigin(origins = ReactAppProperties.HOST_NAME)
public class AuthController {

    static final String REST_URL = "/api/auth";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        log.debug("Authenticating '{}'", request.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = ((AuthUser) authentication.getPrincipal()).getUser();
        if (!user.isEnabled()) {
            throw new JwtAuthenticationException("User is disabled. Try to contact with administrator.");
        }

        String token = jwtTokenProvider.createToken(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(new AuthResponse(user.getEmail(), token));
    }

    @GetMapping()
    public ResponseEntity<AuthResponse> checkAuth(@AuthenticationPrincipal AuthUser authUser) {
        log.debug("Checking auth");
        String token = jwtTokenProvider.createToken(authUser.getUser());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(new AuthResponse(authUser.getUser().getEmail(), token));
    }
}