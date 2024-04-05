package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.bean.AdminUser;
import com.example.restfulwebservice.bean.AdminUserV2;
import com.example.restfulwebservice.bean.User;
import com.example.restfulwebservice.dao.UserDaoService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable Integer id) throws UserNotFoundException {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            //이 작업을 하는건 조금 불필요 하다. 필드를 하나씩하는건 너무 오래 걸린다.
            //adminUser.setName(user.getName());
            BeanUtils.copyProperties(user,adminUser);
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            BeanUtils.copyProperties(user,adminUser);
            adminUser.setGrade("VIP"); //grade
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"grade");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }

    //Params Start
    @GetMapping(value = "/users/{id}" , params = "version=2")
    public MappingJacksonValue retrieveUserWithParamVersion2(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            BeanUtils.copyProperties(user,adminUser);
            adminUser.setGrade("VIP"); //grade
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"grade");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    @GetMapping(value = "/users/{id}" , params = "version=1")
    public MappingJacksonValue retrieveUserWithParamVersion1(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            //이 작업을 하는건 조금 불필요 하다. 필드를 하나씩하는건 너무 오래 걸린다.
            //adminUser.setName(user.getName());
            BeanUtils.copyProperties(user,adminUser);
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    //Params end

    //Header Start
    @GetMapping(value = "/users/{id}" , headers = "version=1")
    public MappingJacksonValue retrieveUserWithHeaderVersion1(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            //이 작업을 하는건 조금 불필요 하다. 필드를 하나씩하는건 너무 오래 걸린다.
            //adminUser.setName(user.getName());
            BeanUtils.copyProperties(user,adminUser);
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    @GetMapping(value = "/users/{id}" , headers = "version=2")
    public MappingJacksonValue retrieveUserWithHeaderVersion2(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            BeanUtils.copyProperties(user,adminUser);
            adminUser.setGrade("VIP"); //grade
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"grade");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    //Header End

    //MIME START
    @GetMapping(value = "/users/{id}" , produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserWithMIMEVersion1(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            BeanUtils.copyProperties(user,adminUser);
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    @GetMapping(value = "/users/{id}" , produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserWithMIMEVersion2(@PathVariable Integer id) throws UserNotFoundException{
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null){
            throw new UserNotFoundException(String.format("id[%s] not found" , id));
        }else{
            BeanUtils.copyProperties(user,adminUser);
            adminUser.setGrade("VIP"); //grade
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"grade");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
    }
    //MIME END

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUser() throws UserNotFoundException{
        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;
        for (User user: users){
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user , adminUser);

            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate" ,"ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filterProvider);

        return mapping;
    }


}
