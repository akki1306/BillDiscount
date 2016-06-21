package com.assignment.model;

import java.util.List;

import com.assignment.roles.Role;

public class User {
    List<Role> roles;

    public User(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
