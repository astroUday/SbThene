package com.chicmic.sbthene.service;

import com.chicmic.sbthene.models.Admin;
import com.chicmic.sbthene.models.dto.AdminDto;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    public Admin saveAdmin(Admin admin);
    public boolean updateAdmin(Admin admin, MultipartFile file);

}
