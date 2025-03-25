package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.internal.Require;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CallFunctionParameters {

    private Map<String, Object> map = new TreeMap<>();

    public CallFunctionParameters(String functionDeclaration, boolean awaitPromise, Target target) {
        map.put("functionDeclaration", Require.nonNull("functionDeclaration", functionDeclaration));
        map.put("awaitPromise", awaitPromise);
        map.put("target", Require.nonNull("target", target));
    }

    public CallFunctionParameters arguments(List<LocalValue> arguments) {
        map.put("arguments", Require.nonNull("arguments", List.copyOf(arguments)));
        return this;
    }

    public CallFunctionParameters resultOwnership(ResultOwnership resultOwnership) {
        map.put("resultOwnership", Require.nonNull("resultOwnership", resultOwnership));
        return this;
    }

    public CallFunctionParameters serializationOptions(SerializationOptions serializationOptions) {
        map.put("serializationOptions", Require.nonNull("serializationOptions", serializationOptions));
        return this;
    }

    public CallFunctionParameters thisValue(LocalValue thisValue) {
        map.put("this", Require.nonNull("this", thisValue));
        return this;
    }

    public CallFunctionParameters userActivation(boolean userActivation) {
        map.put("userActivation", userActivation);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CallFunctionParameters{");
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
