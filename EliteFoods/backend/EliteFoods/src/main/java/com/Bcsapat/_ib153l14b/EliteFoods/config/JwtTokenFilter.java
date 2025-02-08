package com.Bcsapat._ib153l14b.EliteFoods.config;

import com.Bcsapat._ib153l14b.EliteFoods.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;


@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtTokenFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=getTokenFromRequest(request);


        if (token != null) {
            try {
                if (jwtUtil.isTokenValid(token)) {
                    log.info("Valid!");
                    String username = jwtUtil.extractUsername(token);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                        UserDetails userDetails = userService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token.");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
    private String getTokenFromRequest(HttpServletRequest request){
        Enumeration<String> headers = request.getHeaderNames();
        /*while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            log.info("Header: " + headerName + " Value: " + request.getHeader(headerName));
        }*/

        String header = request.getHeader("Authorization");
        log.info("Authorization header: " + header);

        if(header!=null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
