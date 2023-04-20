package com.chicmic.sbthene.filters;

import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.models.Authority;
import com.chicmic.sbthene.models.UserToken;
import com.chicmic.sbthene.service.Impl.AdminServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AdminServiceImpl adminService;
    private AuthenticationManager authenticationManager;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    public CustomAuthenticationFilter(AdminServiceImpl adminService, AuthenticationManager authenticationManager) {
        this.adminService=adminService;
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication
            (HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
        System.out.println("In CustomAuthenticationFilter");


        String username = null ;
        String password = null;
//        InputStream requestBodyInput;
//        try {
//            byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
//            Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
//            username=jsonRequest.get("email");
//            password=jsonRequest.get("password");
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        username=request.getParameter("email");
        password=request.getParameter("password");
//        System.out.println(request.getHeader("Authorization"));
        System.out.println(  "USERNAME :"+username + "------------------\\\\\\\\\\----------" + password);


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        System.out.println(usernamePasswordAuthenticationToken);
        Authentication authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println(authentication);
        return authentication;
    }


    @Override
    protected void successfulAuthentication
            (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        System.out.println("In successfulAuthentication..");

        Collection<Authority> authorities=new ArrayList<>();
        Admin admin=adminService.getAdminByEmail(authResult.getName());
        authorities.add(new Authority(admin.getRole()));

        UUID uuid=UUID.randomUUID();
        User temp= (User) authResult.getPrincipal();

        Admin admin1 =  adminService.getAdminByEmail(temp.getUsername());

    // updating token table
        UserToken userToken=UserToken.builder().admin(admin).uuid(uuid.toString()).build();
        adminService.createUuid(userToken);
//        System.out.println("++++++++++++++++ After build");
//        userToken.setAdmin(admin1);
//        admin1.getUserTokenSet().add(userToken);
//        admin1.setEmail(request.getParameter("email"));
//        adminService.saveAdmin(admin1);



    //save uuid in JSessionId
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if("JSESSIONID".equals(cookie.getName())){
                    cookie.setValue(uuid.toString());
                    response.addCookie(cookie);break;
                }
            }
        }

        Cookie cookie=new Cookie("AuthHeader",uuid.toString());
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);

//        HttpSession session=request.getSession(true);
//        session.setAttribute("token",uuid.toString());


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(admin1,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

//        String redirectUrl= determineTargetUrl(authResult);

        request.setAttribute("Authorization",uuid.toString());
        String redirectUrl="/dashboard";
        redirectStrategy.sendRedirect(request,response,redirectUrl);

        System.out.println("-------------------------------------------------------------"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        System.out.println("-------------------------------------------------------------"+redirectUrl);
        System.out.println(request.getAttribute("Authorization"));

    }
    protected String determineTargetUrl(final Authentication authentication){
        Map<String, String> roleToUri=new HashMap<>();
        roleToUri.put("DRIVER","/driver-profile");
        roleToUri.put("RIDER","/rider-profile");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for(final GrantedAuthority grantedAuthority:authorities){
            String authorityName=grantedAuthority.getAuthority();
            if(roleToUri.containsKey(authorityName))return roleToUri.get(authorityName);
        }
        throw new IllegalStateException();
    }
















    //    @Override
//    protected void doFilterInternal(
//            @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
//            throws ServletException, IOException {
//
//
//
//
//
////            final String authHeader=request.getHeader("Authorization");
////            final String jwt;
////            final String username;
////
////            if(authHeader==null || !authHeader.startsWith("Bearer ")){
////                filterChain.doFilter(request,response); return;
////            }
////
////            jwt=authHeader.substring(7);
////            username=jwtService.extractUsername(jwt);
//
//    }
}
