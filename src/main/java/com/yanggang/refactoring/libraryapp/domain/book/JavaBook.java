package com.yanggang.refactoring.libraryapp.domain.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class JavaBook {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public JavaBook() {

    }

    public JavaBook(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
