package com.edu.eksamenbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Genre {
    POP, ROCK, JAZZ, HIPHOP, ELECTRONIC, CLASSICAL;

    @JsonCreator
    public static Genre fromString(String value) {
        for (Genre genre : Genre.values()) {
            if (genre.name().equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid Genre: " + value);
    }
}