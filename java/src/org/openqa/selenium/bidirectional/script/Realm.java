package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.internal.Require;

public class Realm {

    private final String value;

    public Realm(String value) {
        this.value = Require.nonNull("value", value);
    }

    @Override
    public String toString() {
        return value;
    }

    private String toJson() {
        return value;
    }

    private static Realm fromJson(String json) {
        return new Realm(json);
    }
}
