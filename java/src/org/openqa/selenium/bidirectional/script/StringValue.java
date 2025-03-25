package org.openqa.selenium.bidirectional.script;

import java.util.Map;

public class StringValue extends PrimitiveProtocolValue {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public Map<String, Object> asMap() {
        return Map.of("type", "null", "value", value);
    }

    private static StringValue fromJson(Map<String, Object> json) {
        if (json.get("type").equals("string")) {
            return new StringValue((String) json.get("value"));
        }
        throw new IllegalArgumentException("Unknown type: " + json.get("type"));
    }
}
