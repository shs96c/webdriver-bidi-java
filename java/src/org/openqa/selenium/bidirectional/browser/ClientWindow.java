package org.openqa.selenium.bidirectional.browser;

import java.util.Map;

public class ClientWindow {
    private final String id;

    public ClientWindow(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

