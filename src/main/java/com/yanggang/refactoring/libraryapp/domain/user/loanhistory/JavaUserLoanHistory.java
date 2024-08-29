package com.yanggang.refactoring.libraryapp.domain.user.loanhistory;

import com.yanggang.refactoring.libraryapp.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.jetbrains.annotations.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class JavaUserLoanHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String bookName;

    private boolean isReturn;

    public JavaUserLoanHistory() {

    }

    public JavaUserLoanHistory(User user, String bookName, boolean isReturn) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = isReturn;
    }

    public void doReturn() {
        this.isReturn = true;
    }

    @NotNull
    public String getBookName() {
        return this.bookName;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    public boolean isReturn() {
        return isReturn;
    }
}
