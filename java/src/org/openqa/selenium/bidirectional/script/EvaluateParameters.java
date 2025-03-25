package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.internal.Require;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EvaluateParameters {

    private Map<String, Object> map = new TreeMap<>();

    public EvaluateParameters(String expression, Target target, boolean awaitPromise) {
        map.put("expression", Require.nonNull("Expression", expression));
        map.put("target", Require.nonNull("Target", target));
        map.put("awaitPromise", awaitPromise);
    }

    public EvaluateParameters resultOwnership(ResultOwnership ownership) {
        map.put("resultOwnership", ownership.toString());
        return this;
    }

    public EvaluateParameters serializationOptions(SerializationOptions serializationOptions) {
        map.put("serializationOptions", serializationOptions.toJson());
        return this;
    }

    public EvaluateParameters userActivation(boolean userActivation) {
        map.put("userActivation", userActivation);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EvaluateParameters{");
        sb.append(
                map.entrySet().stream()
                        .map(e -> e.getKey() + "=" + e.getValue())
                        .collect(Collectors.joining(", ")));
        sb.append('}');
        return sb.toString();
    }

    public Map<String, Object> asMap() {
        return Map.copyOf(map);
    }
}
