package com.example.restfulwebservice.repository;

import com.example.restfulwebservice.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
