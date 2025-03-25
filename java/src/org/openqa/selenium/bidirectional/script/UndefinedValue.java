package org.openqa.selenium.bidirectional.script;

import java.util.Map;

public class UndefinedValue extends PrimitiveProtocolValue {

    public Map<String, Object> asMap() {
        return Map.of("type", "undefined");
    }

    private static UndefinedValue fromJson(Map<String, Object> json) {
        if (json.get("type").equals("undefined")) {
            return new UndefinedValue();
        }
        throw new IllegalArgumentException("Unknown type: " + json.get("type"));
    }
}
