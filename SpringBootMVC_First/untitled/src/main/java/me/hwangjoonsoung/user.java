package me.hwangjoonsoung;

public class user {

    private String name;
    private int age;

    public user update(int age){
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
