package org.openqa.selenium.bidirectional.script;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class SerializationOptions {
    public enum IncludeShadowTree {
        NONE,
        OPEN,
        ALL
    }

    private Optional<Long> maxDomDepth = Optional.empty();
    private Optional<Long> maxObjectDepth = Optional.empty();
    private Optional<org.openqa.selenium.bidi.script.SerializationOptions.IncludeShadowTree> includeShadowTree = Optional.empty();

    public void setMaxDomDepth(long value) {
        maxDomDepth = Optional.of(value);
    }

    public void setMaxObjectDepth(long value) {
        maxObjectDepth = Optional.of(value);
    }

    public void setIncludeShadowTree(org.openqa.selenium.bidi.script.SerializationOptions.IncludeShadowTree value) {
        includeShadowTree = Optional.of(value);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> toReturn = new TreeMap<>();
        maxDomDepth.ifPresent(value -> toReturn.put("maxDomDepth", value));
        maxObjectDepth.ifPresent(value -> toReturn.put("maxObjectDepth", value));
        includeShadowTree.ifPresent(value -> toReturn.put("includeShadowTree", value.toString()));
        return Collections.unmodifiableMap(toReturn);
    }
}
