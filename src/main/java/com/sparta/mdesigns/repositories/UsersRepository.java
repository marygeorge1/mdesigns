package com.sparta.mdesigns.repositories;

import com.sparta.mdesigns.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
