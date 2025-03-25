package org.openqa.selenium.bidirectional.script;

import java.util.Map;

import static org.openqa.selenium.bidirectional.script.ValueHelper.mapToRemoteValue;

public interface RemoteValue {

    private static RemoteValue fromJson(Map<String, Object> raw) {
        return mapToRemoteValue(raw);
    }

}
