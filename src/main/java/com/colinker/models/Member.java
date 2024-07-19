package com.colinker.models;

public class Member {
    private String user;
    private String role;
    private boolean isBlocked;

    public Member() {}

    public Member(String user, String role, boolean isBlocked) {
        this.user = user;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
}