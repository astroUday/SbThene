package com.chicmic.sbthene.repo;

import com.chicmic.sbthene.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AdminRepo extends JpaRepository<Admin,Long> {
    Admin findByEmail(String username);
    Admin findByName(String name);
    Admin findByNumber(Integer phone);
    Admin findByDateOfBirth(Date date);
}
