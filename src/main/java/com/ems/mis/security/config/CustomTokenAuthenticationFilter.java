package com.ems.mis.security.config;
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class CustomTokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
<<<<<<< HEAD


    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();
    public void storeToken(String token, String username) {
        tokenStore.put(token, username);
    }
    public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }
    public String getUsernameFromToken(String token) {
        return tokenStore.get(token);
    }
=======
)
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(String token, String username) {
        tokenStore.put(token, username);
    }

    public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

    public String getUsernameFromToken(String token) {
        return tokenStore.get(token);
    }

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

<<<<<<< HEAD
        // Validate 6-character token
=======
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
        if (validateToken(token)) {
            String username = getUsernameFromToken(token);
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
        filterChain.doFilter(request, response);
    }
}