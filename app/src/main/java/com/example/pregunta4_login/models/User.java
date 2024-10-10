package com.example.pregunta4_login.models;

public class User {
    private String name;
    private String user;
    private String password;
    private String rol;

    public User(){

    }
    public User(String name,String user,String password,String rol) {
        this.name = name;
        this.user=user;
        this.password=password;
        this.rol=rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
