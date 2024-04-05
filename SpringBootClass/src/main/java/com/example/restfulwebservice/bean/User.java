package com.example.restfulwebservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

//import javax.validation.constraints.Past;
//import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"ssn" , "password"})
public class User {


    private Integer id;
    @Size(min = 2 , message = "두글자 이상 선택 해주세요")
    private String name;
    @Past(message = "과거날짜만 선택 가능합니다.")
    private Date joinDate;

    private String password;
    private String ssn;

}
