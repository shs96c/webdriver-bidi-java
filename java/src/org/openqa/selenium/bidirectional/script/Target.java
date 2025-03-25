package org.openqa.selenium.bidirectional.script;

import java.util.HashMap;
import java.util.Map;

public abstract class Target {
    protected final Map<String, Object> map = new HashMap<>();

    public Map<String, Object> toMap() {
        return this.map;
    }
}
