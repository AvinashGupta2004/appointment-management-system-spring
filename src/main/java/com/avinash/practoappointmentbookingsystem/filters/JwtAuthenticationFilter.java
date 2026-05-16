package com.avinash.practoappointmentbookingsystem.filters;

import com.avinash.practoappointmentbookingsystem.security.CustomUserDetailsService;
import com.avinash.practoappointmentbookingsystem.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    private String parseJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authentication");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = parseJwtToken(request);

            if (token != null && jwtUtils.validateJwtToken(token)) {
                String emailAddress = jwtUtils.extractEmailFromToken(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(emailAddress);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        catch (Exception ex) {

            System.out.println(
                    "Cannot set user authentication: "
                            + ex.getMessage()
            );
        }
        filterChain.doFilter(request, response);
    }
}
