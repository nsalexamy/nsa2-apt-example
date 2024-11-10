/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example.app;

import org.example.model.User;
import org.example.model.UserBuilder;

public class App {
    public static void main(String[] args) {


        User john = UserBuilder.builder()
                .username("John")
                .password("password")
                .enabled(true)
                .build();

        System.out.println("user: " + john.toString());

    }
}
