package com.junction.vk.domain;

import java.util.stream.Stream;

public enum LookingForType {
    MAN("2"),
    WOMAN("1"),
    NOT_KNOWN("0");

    private final String name;

    LookingForType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LookingForType findTypeByName(String name) {
        return Stream.of(values())
                .filter(type -> type.getName().equals(name))
                .findFirst()
                .orElse(NOT_KNOWN);
    }
}
