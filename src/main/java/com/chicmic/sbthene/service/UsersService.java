package com.chicmic.sbthene.service;

import com.chicmic.sbthene.models.Users;

import java.util.List;

public interface UsersService {
    public Users createUser(Users users);
    public Users updateUser(Long id,Users users);
    public void deleteUser(Long id);
    public List<Users> getAllUsers();
}
