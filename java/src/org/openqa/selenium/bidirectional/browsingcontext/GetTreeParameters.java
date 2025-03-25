package org.openqa.selenium.bidirectional.browsingcontext;

import java.util.HashMap;
import java.util.Map;

public class GetTreeParameters {

    private final int maxDepth;
    private final BrowsingContext root;

    public GetTreeParameters(int maxDepth, BrowsingContext root) {
        this.maxDepth = maxDepth;
        this.root = root;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> toReturn = new HashMap<>();
        if (maxDepth > 0) {
            toReturn.put("maxDepth", maxDepth);
        }
        if (root != null) {
            toReturn.put("root", root);
        }
        return Map.copyOf(toReturn);
    }
}
