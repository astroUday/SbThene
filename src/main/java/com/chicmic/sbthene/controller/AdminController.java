package com.chicmic.sbthene.controller;

import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.service.Impl.AdminServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;
    @PostMapping("login")
    public void Login(){
        System.out.println("Logged in successfully !");
    }

    @PostMapping("update-profile")
    public void updateAdmin( @ModelAttribute Admin admin, HttpServletResponse response ,@RequestParam("file") MultipartFile file) throws IOException {
        if(admin!=null) {
            System.out.println("ADMIN ++++++++++++++++++++++++++++++++++++++++ "+admin);
            adminService.updateAdmin(admin,file);
        }
        response.sendRedirect("/edit-profile");
    }
}
