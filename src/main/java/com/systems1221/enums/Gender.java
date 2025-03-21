package com.systems1221.enums;

public enum Gender {
    MALE("Мужчина"),
    FEMALE("Женщина");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
