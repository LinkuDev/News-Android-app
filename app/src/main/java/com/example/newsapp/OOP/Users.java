package com.example.newsapp.OOP;

public class Users {
    private String Name;
    private String Sdt;
    private String Email;

    public Users(String name, String sdt, String email) {
        Name = name;
        Sdt = sdt;
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
