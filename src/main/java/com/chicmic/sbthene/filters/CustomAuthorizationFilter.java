package com.chicmic.sbthene.filters;


import com.chicmic.sbthene.exception.CustomApiExceptionHandler;
import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.models.Authority;
import com.chicmic.sbthene.service.Impl.AdminServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private AdminServiceImpl adminService;
    public CustomAuthorizationFilter(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }
//
    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println(request.getServletPath());

        if(request.getServletPath().equals("/")||request.getServletPath().equals("")
            || request.getServletPath().contains("assets") || request.getServletPath().contains("favicon")
                || request.getServletPath().contains("lib") || request.getServletPath().contains("login")
                 ){
            filterChain.doFilter(request,response);
        }
        else{
            String token=null;
            token=Arrays.stream(request.getCookies())
                    .filter(c->c.getName().equals("AuthHeader")).findFirst()
                    .map(Cookie::getValue).orElse(null).toLowerCase();

            System.out.println("BEFORE FINDING");
            System.out.println(token.substring(0));
            if(token==null || adminService.findUserFromUUID(token.substring(0))==null){
                String redirectUrl="/";
                new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);
                throw new CustomApiExceptionHandler(HttpStatus.BAD_REQUEST,"Please give Valid Token Id");
            }
            else {
//                Long tempId=adminService.findUserFromUUID(token.substring(0));
                Admin temp=adminService.findUserFromUUID(token);
                Collection<Authority> authorities=new ArrayList<>();
                authorities.add(new Authority(temp.getRole()));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(temp,null,authorities);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println(" Doing do filter for curr user");
            }

            System.out.println("abcd do filter");
            filterChain.doFilter(request,response);return;
        }



        }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
