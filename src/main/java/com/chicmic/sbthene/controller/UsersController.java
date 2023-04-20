package com.chicmic.sbthene.controller;

import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.models.SearchResults;
import com.chicmic.sbthene.models.Users;
import com.chicmic.sbthene.service.Impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UsersController {
    @Autowired
    private final UsersServiceImpl usersService;

    @GetMapping("/{offset}/{pageSize}")
    public String findAllUsers(Model model,@PathVariable("offset") int offset,@PathVariable("pageSize") int pageSize){
         Page<Users> usersList= usersService.getPaginatedUsers(offset,pageSize,null);
         model.addAttribute("userList",usersList);
         model.addAttribute("currentPage",pageSize);
         model.addAttribute("totalPages",usersList.getTotalPages());
         return "admin-users";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("updatedUser") Users users){
        usersService.updateUser(id,users);
        return "redirect:/admin/users?offset=0&pageSize=5&query";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("newUser") Users users){
        System.out.println(users);
        usersService.createUser(users);
        return "redirect:/admin/users?offset=0&pageSize=5&query";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        usersService.deleteUser(id);
        return "redirect:/admin/users?offset=0&pageSize=5&query";
    }



}
