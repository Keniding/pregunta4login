package com.example.pregunta4_login.models;

import java.util.Date;

public class Personal {
    private String name;
    private String born;
    private String rol;
    private String inputDate;
    private String status;
    private String photo;
    private String user;
    private String password;
    public Personal(String name,String born,String rol,String inputDate,String status,String photo,String user,String password) {
    this.name=name;
    this.born=born;
    this.rol=rol;
    this.inputDate=inputDate;
    this.status=status;
    this.photo=photo;
    this.user=user;
    this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
}
