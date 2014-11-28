package ru.tsystems.tsproject.ecare.util;

/**
 * This enum class contains roles of the users in application..
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
