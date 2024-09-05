package com.yanggang.refactoring.libraryapp.dto.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserUpdateRequest {

    private long id;
    private String name;

    @Builder
    public UserUpdateRequest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
