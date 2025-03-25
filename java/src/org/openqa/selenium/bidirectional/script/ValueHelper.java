package org.openqa.selenium.bidirectional.script;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ValueHelper {

    private ValueHelper() {
        // utility class
    }

    @SuppressWarnings("unchecked")
    public static LocalValue mapToLocalValue(Map<String, Object> raw) {
        PrimitiveProtocolValue primitiveValue = mapToPrimitiveProtocolValue(raw);
        if (primitiveValue != null) {
            return primitiveValue;
        }

        String type = (String) raw.get("type");
        Object value = raw.get("value");

        switch (type) {
            case "array":
                Collection<Map<String, Object>> values = (List<Map<String, Object>>) value;
                List<LocalValue> converted = values.stream()
                        .map(ValueHelper::mapToLocalValue)
                        .collect(Collectors.toUnmodifiableList());
                return new ListLocalValue(converted);

            case "date":
                return new DateLocalValue((String) value);

            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public static RemoteValue mapToRemoteValue(Map<String, Object> raw) {
        PrimitiveProtocolValue primitiveValue = mapToPrimitiveProtocolValue(raw);
        if (primitiveValue != null) {
            return primitiveValue;
        }

        throw new IllegalArgumentException("Unknown type: " + raw);
    }

    private static PrimitiveProtocolValue mapToPrimitiveProtocolValue(Map<String, Object> raw) {
        String type = (String) raw.get("type");
        Object value = raw.get("value");

        switch (type) {
            case "null":
                return new NullValue();

            case "string":
                return new StringValue((String) value);

            case "undefined":
                return new UndefinedValue();


        }

        return null;
    }
}
