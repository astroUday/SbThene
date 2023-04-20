package com.chicmic.sbthene.service.Impl;

import com.chicmic.sbthene.models.SearchResults;
import com.chicmic.sbthene.models.Users;
import com.chicmic.sbthene.repo.UsersRepo;
import com.chicmic.sbthene.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepo;
    @Override
    public Users createUser(Users users) {
      return usersRepo.save(users);
    }

    @Override
    public Users updateUser(Long id,Users users) {
        Users existingUser=usersRepo.findById(id).get();
        existingUser.setEmail(users.getEmail());
        existingUser.setName(users.getName());
        existingUser.setEnabled(users.isEnabled());
        existingUser.setPhone(users.getPhone());
        return usersRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        Users users=usersRepo.findById(id).get();
        users.setSoftDeleted(true);
        usersRepo.save(users);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    public Page<Users> getPaginatedUsers(int offset, int pageSize,String query){

        Pageable pageable;
        if(query!=null && !query.isEmpty())pageable = PageRequest.of(offset,pageSize).withSort(Sort.by(query));
        else pageable=PageRequest.of(offset,pageSize);
        Page<Users> usersPage=usersRepo.findBySoftDeleted(false,pageable);
        return usersPage;
    }

    public Users getUserById(Long id){
        return usersRepo.findById(id).get();
    }

    public List<Users> search(String query) {
        List<Users> usersList=usersRepo.findByName(query,null).stream().toList();

        List<SearchResults> results=new ArrayList<>();
        for(Users users:usersList){
            SearchResults result=createSearchResult(users);
            if(result!=null)results.add(result);
        }
//        return results;
        return usersRepo.findByName(query,null).stream().toList();
    }

    private SearchResults createSearchResult(Users users) {
        if(users==null||users.getName()==null||users.getRole()==null)return null;
        SearchResults result=new SearchResults(users.getName(),users.getRole());
        return result;
    }

    public Users findById(Long id) {
        return usersRepo.findById(id).get();
    }

    public Page<Users> getSearchedUsers(Integer offset, Integer pageSize, String query) {
        Pageable pageable;
        pageable = PageRequest.of(offset,pageSize).withSort(Sort.by("name"));
        Page<Users> usersPage=usersRepo.findByName(query,pageable);
        return usersPage;
    }
}
