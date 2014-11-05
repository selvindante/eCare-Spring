package ru.tsystems.tsproject.ecare.util;

/**
 * Created by Selvin
 * on 31.10.2014.
 */
public enum Action {
    LOGIN("login"),
    LOGOUT("logout"),
    REGISTRATION("registration"),
    VIEW_DASHBOARD("viewDashboard"),
    SEARCH_CLIENT_BY_NUMBER("searchClientByNumber"),
    VIEW_ALL_TARIFFS("viewAllTariffs"),
    DELETE_CLIENT("deleteClient"),
    DELETE_ALL_CLIENTS("deleteAllClients"),
    CHOOSE_TARIFF("chooseTariff"),
    VIEW_CONTRACT("viewContract"),
    DELETE_CONTRACT("deleteContract"),
    BLOCK_BY_OPERATOR("blockByOperator"),
    UNBLOCK_BY_OPERATOR("unblockByOperator"),
    BLOCK_BY_CLIENT("blockByClient"),
    UNBLOCK_BY_CLIENT("unblockByClient"),
    CHANGE_TARIFF("changeTariff"),
    SET_NEW_TARIFF("setNewTariff"),
    CREATE_TARIFF("createTariff"),
    DELETE_ALL_TARIFFS("deleteAllTariffs"),
    VIEW_TARIFF("viewTariff"),
    DELETE_TARIFF("deleteTariff"),
    CREATE_OPTION("createOption"),
    DELETE_ALL_OPTIONS("deleteAllOptions"),
    VIEW_OPTION("viewOption"),
    EDIT_OPTION("editOption"),
    UPDATE_OPTION("updateOption"),
    DELETE_OPTION("deleteOption"),
    REMOVE_DEPENDENT_OPTION("removeDependentOption"),
    REMOVE_ALL_DEPENDENT_OPTIONS("removeAllDependentOptions"),
    REMOVE_INCOMPATIBLE_OPTION("removeIncompatibleOption"),
    REMOVE_ALL_INCOMPATIBLE_OPTIONS("removeAllIncompatibleOptions");

    String title;

    Action(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
