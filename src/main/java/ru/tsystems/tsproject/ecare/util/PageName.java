package ru.tsystems.tsproject.ecare.util;

/**
 * This enum class contains page names to transfer them to the JSP page header.
 */
public enum PageName {
    LOGIN ("Login"),
    REGISTRATION ("Registration"),
    DASHBOARD ("Dashboard"),
    TARIFFS ("Tariffs"),
    TARIFF ("Tariff"),
    NEW_TARIFF("New tariff"),
    OPTION ("Option"),
    OPTION_SETTINGS("Option settings"),
    NEW_OPTION("New option"),
    CLIENT("Client"),
    EDIT_CLIENT("Edit client"),
    CONTRACT ("Contract"),
    NEW_CONTRACT("New contract"),
    CHOOSE_TARIFF("Choose tariff"),
    CHOOSE_OPTIONS("Choose options");

    private String title;

    PageName(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
