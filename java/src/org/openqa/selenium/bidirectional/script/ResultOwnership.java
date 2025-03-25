package org.openqa.selenium.bidirectional.script;

public enum ResultOwnership {
    ROOT("root"),
    NONE("none");

    private final String ownership;

    ResultOwnership(String ownership) {
        this.ownership = ownership;
    }

    @Override
    public String toString() {
        return ownership;
    }
}
