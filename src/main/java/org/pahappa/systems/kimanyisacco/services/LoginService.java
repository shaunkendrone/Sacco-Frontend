package org.pahappa.systems.kimanyisacco.services;

public interface LoginService{
    boolean authenticate(String email, String password,String status);
}