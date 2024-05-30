package com.example.fakespotify;

public class User {
    public int id ;
    public String name ;
    public String password ;

    public User(int id, String name, String password)
    {
        this.id = id ;
        this.name = name ;
        this.password = password ;
    }

    public int GetID() {return id ;}
    public String GetName() {return name ;}
    public String GetPassword() {return password ;}
}
