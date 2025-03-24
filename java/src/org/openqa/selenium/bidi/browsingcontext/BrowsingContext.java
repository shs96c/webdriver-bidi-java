package org.openqa.selenium.bidi.browsingcontext;

import org.openqa.selenium.json.JsonInput;

public class BrowsingContext {

    private final String context;

    public BrowsingContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return context;
    }

    private String toJson() {
        return context;
    }

    private static BrowsingContext fromJson(JsonInput input) {
        return new BrowsingContext(input.nextString());
    }
}
