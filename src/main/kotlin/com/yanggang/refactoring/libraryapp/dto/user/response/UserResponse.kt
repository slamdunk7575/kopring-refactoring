package com.yanggang.refactoring.libraryapp.dto.user.response;

import com.yanggang.refactoring.libraryapp.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final long id;
    private final String name;
    private final Integer age;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }
}
