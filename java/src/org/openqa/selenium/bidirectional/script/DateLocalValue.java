package org.openqa.selenium.bidirectional.script;

import java.util.Map;

public class DateLocalValue implements LocalValue {

    private final String value;

    DateLocalValue(String value) {
        this.value = value;
    }

    public Map<String, Object> toJson() {
        return Map.of("type", "date", "value", value);
    }
}