package com.chicmic.sbthene.service.Impl;

import com.chicmic.sbthene.exception.CustomApiExceptionHandler;
import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.models.Authority;
import com.chicmic.sbthene.models.UserToken;
import com.chicmic.sbthene.models.dto.AdminDto;
import com.chicmic.sbthene.repo.AdminRepo;
import com.chicmic.sbthene.repo.TokenJpaRepo;
import com.chicmic.sbthene.service.AdminService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.chicmic.sbthene.SbtheneApplication.passwordEncoder;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements UserDetailsService, AdminService {

    private final AdminRepo adminRepo;
    private final TokenJpaRepo tokenJpaRepo;
    @Value("${image.path}")
    String imagePath;

    @Override
    public Admin saveAdmin(Admin admin) {
        if(admin==null)return null;
        Admin temp=adminRepo.findByEmail(admin.getEmail());
        temp.setUserTokenSet(admin.getUserTokenSet());
        return adminRepo.save(temp);
    }

    @Override
    public boolean updateAdmin(Admin admin, MultipartFile file) {
        Admin temp= (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin admin1=adminRepo.findByEmail(temp.getEmail());

        if(!admin.getEmail().trim().isEmpty())admin1.setEmail(admin.getEmail());
        if(!admin.getName().trim().isEmpty() )admin1.setName(admin.getName());
        if(admin.getDateOfBirth()!=null)admin1.setDateOfBirth(admin.getDateOfBirth());
        if(!admin.getPassword().trim().isEmpty())admin1.setPassword(passwordEncoder().encode(admin.getPassword()));
        if(admin.getNumber()!=null)admin1.setNumber(admin.getNumber());

        // saving file
        try{
            if(!file.isEmpty()){

                String uniQueFileName=admin1.getAdminId()+"id="+file.getOriginalFilename();
                admin1.setImage(uniQueFileName);

                byte[] bytes=file.getBytes();
                String location=imagePath;
                Path path=Paths.get(location+uniQueFileName);
                Files.write(path,bytes);
//                File file1 = new ClassPathResource("/static/assets").getFile();
//                Path path = Paths.get(file1.getAbsolutePath() + File.separator + uniQueFileName);
////                ClassPathResource resource= new ClassPathResource("/home/chicmic/Downloads/sbthene/src/main/resources/static/assets/img");
////                Path uploadedPath=Paths.get(resource.getURI());
//                Path filePath= path.resolve(uniQueFileName);
//                System.out.println("PATH ======= "+filePath);
//                Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
//                System.out.println("FILE SAVED !!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }catch (Exception e){
        e.printStackTrace();
    }
        adminRepo.save(admin1);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin= adminRepo.findByEmail(username);
        if(admin==null)throw new CustomApiExceptionHandler(HttpStatus.NOT_FOUND,username + "doesn't exists, please REGISTER.");

        Collection<Authority> authorites=new ArrayList<>();
        authorites.add(new Authority(admin.getRole()));

        return new User(admin.getEmail(),admin.getPassword(),authorites);
    }

    public Admin getAdminByEmail(String name) {
        return adminRepo.findByEmail(name);
    }

    public void createUuid(UserToken userToken) {
        tokenJpaRepo.save(userToken);
    }

    public Admin findUserFromUUID(String authorization) {
        return tokenJpaRepo.findByUuid(authorization).getAdmin();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if("AuthHeader".equals(cookie.getName())){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);break;
                }
            }
        }
    }

    public Admin findAdminFromId(Long tempId) {
        return adminRepo.findById(tempId).get();
    }

    public Admin findAdminFromEmail(String email) {
        return adminRepo.findByEmail(email);
    }
}
