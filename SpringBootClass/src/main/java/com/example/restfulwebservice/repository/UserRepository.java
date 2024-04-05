package com.example.restfulwebservice.repository;

import com.example.restfulwebservice.bean.User;
import com.example.restfulwebservice.bean.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJPA, Integer> {
}
