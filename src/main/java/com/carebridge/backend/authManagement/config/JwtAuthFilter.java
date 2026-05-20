package com.carebridge.backend.authManagement.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.carebridge.backend.authManagement.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
    
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{

         String path = request.getServletPath();

    if (
            path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs")
            || path.equals("/swagger-ui.html")
            || path.startsWith("/auth")
    ) {

        filterChain.doFilter(request, response);
        return;
    }


        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // String token = authHeader.substring(7);

        // String email = jwtUtil.extractEmail(token);

        // String role = jwtUtil.extractRole(token);

        // UsernamePasswordAuthenticationToken authentication = 
        //         new UsernamePasswordAuthenticationToken(
        //             email,
        //             null,
        //             List.of(new SimpleGrantedAuthority("ROLE_"+role))
        //         );

        // authentication.setDetails(
        //     new WebAuthenticationDetailsSource().buildDetails(request)
        // );
        
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        try{

    String token = authHeader.substring(7);

    String email = jwtUtil.extractEmail(token);

    String role = jwtUtil.extractRole(token);

    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + role
                            )
                    )
            );

    authentication.setDetails(
            new WebAuthenticationDetailsSource()
                    .buildDetails(request)
    );

    SecurityContextHolder.getContext()
            .setAuthentication(authentication);

}catch (ExpiredJwtException e){

    response.setStatus(
            HttpServletResponse.SC_UNAUTHORIZED
    );

    response.getWriter().write(
            "JWT token expired"
    );

    return;

}catch (Exception e){

    response.setStatus(
            HttpServletResponse.SC_UNAUTHORIZED
    );

    response.getWriter().write(
            "Invalid JWT token"
    );

    return;
}

        filterChain.doFilter(request, response);
    }
}
