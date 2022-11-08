package com.example.relaxtep;

public class user {
    String name;
    String password;
    String phoneno;
    String email;
    String uid;
    String username;
    String usertype;

    public user(String name, String password, String phoneno, String email, String uid, String username, String usertype) {
        this.name = name;
        this.password = password;
        this.phoneno = phoneno;
        this.email = email;
        this.uid = uid;
        this.username = username;
        this.usertype = usertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
