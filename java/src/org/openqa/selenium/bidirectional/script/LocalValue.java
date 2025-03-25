package org.openqa.selenium.bidirectional.script;

import java.util.Map;

import static org.openqa.selenium.bidirectional.script.ValueHelper.mapToLocalValue;

public interface LocalValue {

    private LocalValue fromJson(Map<String, Object> raw) {
        return mapToLocalValue(raw);
    }
}
