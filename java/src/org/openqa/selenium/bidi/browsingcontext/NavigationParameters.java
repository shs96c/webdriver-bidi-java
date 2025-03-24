package org.openqa.selenium.bidi.browsingcontext;

import java.util.HashMap;
import java.util.Map;

public class NavigationParameters {
    private final BrowsingContext context;
    private final String url;
    private final ReadinessState wait;

    public NavigationParameters(BrowsingContext context, String url, ReadinessState wait) {
        this.context = context;
        this.url = url;
        this.wait = wait;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> toReturn = new HashMap<>();

        toReturn.put("context", context);
        toReturn.put("url", url);
        if (wait != null) {
            toReturn.put("wait", wait);
        }

        return Map.copyOf(toReturn);
    }

}
