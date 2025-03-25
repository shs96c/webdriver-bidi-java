package org.openqa.selenium.bidirectional.browsingcontext;

public enum ReadinessState {
    NONE("none"),
    INTERACTIVE("interactive"),
    COMPLETE("complete");

    private final String text;

    ReadinessState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.valueOf(text);
    }
}
