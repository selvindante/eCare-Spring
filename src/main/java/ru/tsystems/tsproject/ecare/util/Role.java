package ru.tsystems.tsproject.ecare.util;

/**
 * Created by Selvin
 * on 26.11.2014.
 */
public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS");

    String value;

    Role(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
