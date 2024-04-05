package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.bean.CountAndUser;
import com.example.restfulwebservice.bean.Post;
import com.example.restfulwebservice.bean.UserJPA;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.repository.PostRepository;
import com.example.restfulwebservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class JpaController {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public JpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<UserJPA> findAll(){
        List<UserJPA> all = userRepository.findAll();
        return all;
    }

    /*responseEntity controll 하는법*/
    @GetMapping("/users2")
    public ResponseEntity findAll2(){
        List<UserJPA> all = userRepository.findAll();
        long count = userRepository.count();
        CountAndUser countAndUser = new CountAndUser();
        countAndUser.setCount((int) count);
        countAndUser.setUsers(all);

        return ResponseEntity.ok(countAndUser);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity findId(@PathVariable int id){
        Optional<UserJPA> byId = userRepository.findById(id);
        if(!byId.isPresent()){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }

        EntityModel entityModel = EntityModel.of(byId.get());
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).findAll());
        entityModel.add(webMvcLinkBuilder.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PutMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserJPA userJPA){
        userRepository.save(userJPA);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userJPA.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserAllPost(@PathVariable int id){
        Optional<UserJPA> byId = userRepository.findById(id);
        if (!byId.isPresent()){
            throw new UserNotFoundException("id not found");
        }
        return byId.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity postUserPost(@PathVariable int id ,@RequestBody Post post){

        Optional<UserJPA> optionalUserJPA = userRepository.findById(id);

        if(!optionalUserJPA.isPresent()){
            throw new UserNotFoundException("user not found");
        }
        UserJPA userJPA = optionalUserJPA.get();

        post.setUser(userJPA);

        postRepository.save(post);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
