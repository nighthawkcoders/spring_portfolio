package com.nighthawk.hacks.streamChain;

import java.util.Arrays;
import java.util.List;

public enum State {
    CALIFORNIA(Arrays.asList(Climate.SUBTROPICAL, Climate.TEMPERATE)),
    FLORIDA(Arrays.asList(Climate.TROPICAL, Climate.SUBTROPICAL)),
    WASHINGTON(Arrays.asList(Climate.TEMPERATE)),
    HAWAII(Arrays.asList(Climate.TROPICAL));

    private List<Climate> climates;

    State(List<Climate> climates) {
        this.climates = climates;
    }

    public List<Climate> getClimates() {
        return climates;
    }
}