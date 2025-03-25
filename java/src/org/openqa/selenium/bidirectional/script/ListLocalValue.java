package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.internal.Require;

import java.util.List;

public class ListLocalValue implements LocalValue {

    private final List<LocalValue> value;

    public ListLocalValue(List<LocalValue> value) {
        this.value = List.copyOf(Require.nonNull("value", value));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ListLocalValue{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    protected List<LocalValue> toJson() {
        return value;
    }
}
