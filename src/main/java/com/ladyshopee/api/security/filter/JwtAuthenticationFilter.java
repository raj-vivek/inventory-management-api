package com.ladyshopee.api.security.filter;

import com.ladyshopee.api.service.JwtService;
import com.ladyshopee.api.service.MongoAuthUserDetailService;
import com.ladyshopee.api.service.impl.MongoAuthUserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MongoAuthUserDetailService mongoAuthUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String jwt = parseJwt(request);

        if (jwt != null){
            String username = jwtService.extractUserName(jwt);

            if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = mongoAuthUserDetailService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // This is more commonly used. This approach relies on the existing context. If a context already exists for the current thread, it will be used. If not, a new one may be created.
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException
//    {
//        Cookie[] cookies = request.getCookies();
//
//        if(cookies == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("jwt")) {
//                String jwt = cookie.getValue();
//                String username = jwtService.extractUserName(jwt);
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = this.mongoAuthUserDetailService.loadUserByUsername(username);
//
//                    if (jwtService.isTokenValid(jwt, userDetails)) {
//                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                        // This is more commonly used. This approach relies on the existing context.
//                        // If a context already exists for the current thread, it will be used. If not, a new one may be created.
//                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//                        // This approach explicitly creates a new empty context using
//                        // SecurityContext context = SecurityContextHolder.createEmptyContext();
//                        // context.setAuthentication(authenticationToken);
//                        // SecurityContextHolder.setContext(context);
//                    }
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
}
