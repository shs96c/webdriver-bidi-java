package org.openqa.selenium.bidirectional.browser;

public enum ClientWindowState {
    FULLSCREEN("fullscreen"),
    MAXIMIZED("maximized"),
    MINIMIZED("minimized"),
    NORMAL("normal");

    private final String state;

    ClientWindowState(String state) {
        this.state = state;
    }

    public String toString() {
        return state;
    }

    public static ClientWindowState fromString(String state) {
        for (ClientWindowState windowState : values()) {
            if (windowState.state.equals(state)) {
                return windowState;
            }
        }
        throw new IllegalArgumentException("Invalid window state: " + state);
    }
}
