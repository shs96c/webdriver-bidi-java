package org.openqa.selenium.bidirectional.browser;

import org.openqa.selenium.json.JsonInput;

public class UserContext {

    private final String context;

    public UserContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return context;
    }

    private static UserContext fromJson(JsonInput input) {
        return new UserContext(input.nextString());
    }
}