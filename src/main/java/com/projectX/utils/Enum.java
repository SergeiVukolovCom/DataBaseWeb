package com.projectX.utils;

public enum Enum {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    SIXTEEN(16),
    THIRTYONE(31);

    private final int value;

    Enum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}