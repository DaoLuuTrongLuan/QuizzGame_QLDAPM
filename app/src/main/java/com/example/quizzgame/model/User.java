package com.example.quizzgame.model;

public class User {
    private String idUser;
    private String username;
    private String password;
    private String name;
    private String role;
    private String typeAccount;
    private String email;

    public User() {

    }
    public User(String name, String username, String password, String role, String typeAccount, String idUser, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.typeAccount = typeAccount;
        this.idUser = idUser;
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
