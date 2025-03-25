package org.openqa.selenium.bidirectional.script;

public class ContextTarget extends Target {
    public ContextTarget(String id) {
        super.map.put("context", id);
    }

    public ContextTarget(String id, String sandbox) {
        super.map.put("context", id);
        super.map.put("sandbox", sandbox);
    }
}
