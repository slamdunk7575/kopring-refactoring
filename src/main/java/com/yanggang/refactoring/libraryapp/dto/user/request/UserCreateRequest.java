package com.yanggang.refactoring.libraryapp.dto.user.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {

    private String name;
    private Integer age;

    public UserCreateRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
