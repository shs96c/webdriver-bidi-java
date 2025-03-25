package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.json.JsonInput;
import org.openqa.selenium.json.TypeToken;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.unmodifiableMap;

public class StackTrace {

    private final List<StackFrame> callFrames;

    private StackTrace(List<StackFrame> callFrames) {
        this.callFrames = callFrames;
    }

    public List<StackFrame> getCallFrames() {
        return callFrames;
    }

    public static StackTrace fromJson(JsonInput input) {

        List<StackFrame> callFrames = Collections.emptyList();

        input.beginObject();
        while (input.hasNext()) {
            if ("callFrames".equals(input.nextName())) {
                callFrames = input.read(new TypeToken<List<StackFrame>>() {}.getType());
            } else {
                input.skipValue();
            }
        }

        input.endObject();

        return new StackTrace(callFrames);
    }

    private Map<String, Object> toJson() {
        Map<String, Object> toReturn = new TreeMap<>();
        toReturn.put("callFrames", callFrames);

        return unmodifiableMap(toReturn);
    }
}
