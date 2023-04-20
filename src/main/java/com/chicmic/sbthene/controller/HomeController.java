package com.chicmic.sbthene.controller;

import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.models.Users;
import com.chicmic.sbthene.service.Impl.AdminServiceImpl;
import com.chicmic.sbthene.service.Impl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final AdminServiceImpl adminService;
    private final UsersServiceImpl usersService;
    @GetMapping
    public String HomePage(Model model){
//        UUID uuid=UUID.randomUUID();
//        model.addAttribute("token",uuid.toString());
//        System.out.println(uuid);
        return "index";
    }
    @PostMapping("search")
    public String gettUserBySearch(Model model,@RequestParam String query){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
//        List<Users> usersList= usersService.getAllUsers();
//        model.addAttribute("userList",usersList);
//        System.out.println(usersList);

        Page<Users> usersList= usersService.getSearchedUsers(0,5,query);
        model.addAttribute("userList",usersList);
        model.addAttribute("currentPage",0);
        model.addAttribute("totalPages",usersList.getTotalPages());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "drivers";
    }

    @GetMapping("edit-profile")
    public String editProfile( Model model, HttpServletRequest request){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());

        String name=admin.getImage();
        model.addAttribute("profile",name);
        System.out.println(name +"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return "edit-profile";
    }
    @GetMapping("logout-user")
    public void logOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        adminService.logout(request,response);
        response.sendRedirect("/");
    }
    @GetMapping("dashboard")
    public String DashBoard(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());

        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "dashboard";
    }
    @GetMapping("driver-overview")
    public String driverOverview(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "driver-overview";
    }
    @GetMapping("admin/users")
    public String findPaginatedUsers(Model model,@RequestParam("offset") Integer offset,@RequestParam("pageSize") Integer pageSize,@RequestParam String query){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
//        List<Users> usersList= usersService.getAllUsers();
//        model.addAttribute("userList",usersList);
//        System.out.println(usersList);

        Page<Users> usersList= usersService.getPaginatedUsers(offset,pageSize,query);
        model.addAttribute("userList",usersList);
        model.addAttribute("currentPage",offset);
        model.addAttribute("totalPages",usersList.getTotalPages());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "drivers";
    }

//    @GetMapping("admin/sortUser/{offset}/{pageSize}/")
//    public String findSortedPaginatedUsers(Model model,@PathVariable("offset") Integer offset,@PathVariable("pageSize") Integer pageSize,@RequestParam("query") String query){
//        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
//        model.addAttribute("username",admin.getName());
//        model.addAttribute("timeNow", LocalDateTime.now());
//        model.addAttribute("welcome","Welcome "+admin.getName());
////        List<Users> usersList= usersService.getAllUsers();
////        model.addAttribute("userList",usersList);
////        System.out.println(usersList);
//
//        Page<Users> usersList= usersService.getPaginatedUsers(offset,pageSize,query);
//        model.addAttribute("userList",usersList);
//        model.addAttribute("currentPage",offset);
//        model.addAttribute("totalPages",usersList.getTotalPages());
//        String name=admin.getImage();
//        model.addAttribute("profile",name);
//        return "drivers";
//    }

//    @GetMapping("admin-users/{offset}/{pageSize}")
//    public String findAllUsers(Model model,@PathVariable("offset") int offset,@PathVariable("pageSize") int pageSize){
//        Page<Users> usersList= usersService.getPaginatedUsers(offset,pageSize);
//        model.addAttribute("userList",usersList);
//        model.addAttribute("currentPage",pageSize);
//        model.addAttribute("totalPages",usersList.getTotalPages());
//        return "drivers";
//    }
    @GetMapping("edit-fee-setup")
    public String editFeeSetup(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "edit-fee-setup";
    }
    @GetMapping("fee-setup")
    public String feeSetup(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "fee-setup";
    }
    @GetMapping("fee-setup-detail")
    public String feeSetupDetails(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "fee-setup-detail";}
    @GetMapping("forgot-password")
    public String forgotPassword(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        return "forgot-password";}
    @GetMapping("no-rules")
    public String noRules(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "no-rules";}
    @GetMapping("retailer-overview")
    public String RetailerOverviewer(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "retailer-overview"; }
    @GetMapping("retailer-overview-fees")
    public String retailerOverviewFees(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "retailer-overview-fees";}
    @GetMapping("retailers")
    public String Retailerss(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "retailers";}
    @GetMapping("rules")
    public String Ruless(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
        return "rules";}
    @GetMapping("stores")
    public String stores(Model model){
        Admin admin= (Admin) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",admin.getName());
        model.addAttribute("timeNow", LocalDateTime.now());
        model.addAttribute("welcome","Welcome "+admin.getName());
        String name=admin.getImage();
        model.addAttribute("profile",name);
         return "stores";}

    @GetMapping("user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model){
       Users users= usersService.findById(id);
       model.addAttribute("user",users);
        model.addAttribute("timeNow", LocalDateTime.now());
       return "fee-setup";
    }

}
