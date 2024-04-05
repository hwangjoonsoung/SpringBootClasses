package com.example.restfulwebservice.bean;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountAndUser {

    private int count;
    private List<UserJPA> users;


}
