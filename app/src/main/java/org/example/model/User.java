package org.example.model;

import com.alexamy.nsa2.example.apt.annotations.Nsa2Builder;

@Nsa2Builder
public class User {
    private final String username;
    private final String password;
    private final boolean enabled;

    public User() {
        this("", "", false);
    }

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
