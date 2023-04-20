package com.chicmic.sbthene.repo;

import com.chicmic.sbthene.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepo extends JpaRepository<Users,Long> {
    Page<Users> findByName(String query, Pageable pageable);

    Page<Users> findBySoftDeleted(boolean b, Pageable pageable);
}
