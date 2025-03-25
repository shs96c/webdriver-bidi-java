package org.openqa.selenium.bidirectional.script;

import java.util.Map;

public class NullValue extends PrimitiveProtocolValue {

    public Map<String, Object> asMap() {
        return Map.of("type", "null");
    }

    private static NullValue fromJson(Map<String, Object> json) {
        if (json.get("type").equals("null")) {
            return new NullValue();
        }
        throw new IllegalArgumentException("Unknown type: " + json.get("type"));
    }
}
