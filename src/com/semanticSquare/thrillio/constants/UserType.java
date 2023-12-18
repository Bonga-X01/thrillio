package com.semanticSquare.thrillio.constants;

public enum UserType {
    USER("user"),
    EDITOR("editor"),
    CHIEF_EDITOR("chiefeditor");
    private UserType(String name) {
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }
    public static String getUserType(int val) {

        String userType = null;
        if (val == 0) {
            userType = "USER";
        } else if (val == 1) {
            userType = "EDITOR";
        }
        else if (val == 2) {
            userType = "CHIEF_EDITOR";
        }
        return userType;
    }

}
