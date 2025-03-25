package org.openqa.selenium.bidirectional.browsingcontext;

import org.openqa.selenium.json.JsonInput;

public class NavigationResult {

    private final String navigationId;
    private final String url;

    public String getNavigationId() {
        return navigationId;
    }

    public String getUrl() {
        return url;
    }

    public NavigationResult(String navigationId, String url) {
        this.navigationId = navigationId;
        this.url = url;
    }

    public static org.openqa.selenium.bidi.browsingcontext.NavigationResult fromJson(JsonInput input) {
        String navigationId = null;
        String url = null;

        input.beginObject();
        while (input.hasNext()) {
            switch (input.nextName()) {
                case "navigation":
                    navigationId = input.read(String.class);
                    break;

                case "url":
                    url = input.read(String.class);
                    break;

                default:
                    input.skipValue();
                    break;
            }
        }

        input.endObject();

        return new org.openqa.selenium.bidi.browsingcontext.NavigationResult(navigationId, url);
    }
}
