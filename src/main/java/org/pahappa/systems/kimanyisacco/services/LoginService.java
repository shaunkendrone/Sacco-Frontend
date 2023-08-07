package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.enumerations.Status;

public interface LoginService{
    boolean authenticate(String email, String password,Status status);
}