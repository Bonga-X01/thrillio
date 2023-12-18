package com.semanticSquare.thrillio.constants;

public enum Gender {
    MALE (0),
    FEMALE (1),
    TRANSGENDER (2);
    private Gender(int value) {
        this.value = value;
    }


    private int value;

    public int getValue() {
        return value;
    }
    /**
     * This method determines the gender of the user based on the gender id
     * @param val - represents the gender id
     * @return gender - returns the gender of the user based on val
     * */
    public static Gender getGender(int val) {

        Gender gender = null;
        if (val == 0) {
            gender = Gender.MALE;
        } else if (val == 1) {
            gender = Gender.FEMALE;
        }
        else if (val == 2) {
            gender = Gender.TRANSGENDER;
        }
        return gender;
    }
}
