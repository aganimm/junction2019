package com.junction.vk.domain;

import java.util.stream.Stream;

public enum LookingForType {
    MAN("MAN"),
    WOMAN("WOMAN"),
    NOT_KNOWN("NOT_KNOWN");

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
