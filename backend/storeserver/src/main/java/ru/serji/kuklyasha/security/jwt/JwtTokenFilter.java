package ru.serji.kuklyasha.security.jwt;

import org.springframework.beans.factory.annotation.*;
import org.springframework.lang.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;
import org.springframework.web.servlet.*;
import ru.serji.kuklyasha.error.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token, request);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (AppException ex) {
            resolver.resolveException(request, response, null, ex);
        }
    }
}