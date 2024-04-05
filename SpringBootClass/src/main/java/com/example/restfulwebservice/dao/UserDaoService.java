package com.example.restfulwebservice.dao;

import com.example.restfulwebservice.bean.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1,"hwang",new Date(),"passwrod1" , "111111-1111111" ));
        users.add(new User(2,"Kim",new Date() ,"passwrod2" , "222222-2222222"));
        users.add(new User(3,"Park",new Date() , "passwrod3" , "333333-3333333"));
    }

    public List<User> findAll() {
        System.out.println(users);
        return users;
    }

    public User save(User user){
        int usersCount = users.size();
        if(user.getId() == null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(Integer id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteByID(int id){
        Iterator iterator = users.iterator();

        while (iterator.hasNext()){
            User user = (User) iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }

        }
        return null;
    }

    public User modifyUserById(int id , User user){
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()){
            if(iterator.next().getId()==id){
                iterator.remove();
                users.add(user);
                return user;
            }
        }
    return null;
    }

}
