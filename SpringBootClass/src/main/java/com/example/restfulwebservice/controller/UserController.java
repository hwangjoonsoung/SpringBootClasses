package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.bean.User;
import com.example.restfulwebservice.dao.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }


    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable Integer id) throws UserNotFoundException {
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser= service.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{id}")
    public void removeUser(@PathVariable int id){
        User user = service.deleteByID(id);

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }

    }

    @PutMapping("/users/{id}")
    public void editUser(@PathVariable int id ,@RequestBody User user){
        User resultUser = service.modifyUserById(id, user);
        if (resultUser == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }
    }
    
    //=======================hateoas 적용
    @GetMapping("/hasteoas/users/{id}")
    public EntityModel<User> retrieveUserUsingHeteoas(@PathVariable Integer id) throws UserNotFoundException {
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(webMvcLinkBuilder.withRel("all-users"));

        return entityModel;
    }

}
