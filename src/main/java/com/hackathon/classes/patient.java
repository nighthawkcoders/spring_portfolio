package com.hackathon.classes;

public class patient {
    private boolean isSick;
    private int age;
    private boolean hasHealthInsurance;
    private String name;
    private String emergencyContact;

    public patient(boolean isSick, boolean hasHealthInsurance, int age, String name, String emergencyContact) {
        isSick = isSick;
        hasHealthInsurance = hasHealthInsurance;
        age = age;
        name = name;
        emergencyContact = emergencyContact;
    }

    public boolean willTreat() {
        if (isSick && !hasHealthInsurance) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean needsAdult() {
        if (age < 18 && emergencyContact != "") {
            return true;
        }
        else {
            return false;
        }
    }
}
