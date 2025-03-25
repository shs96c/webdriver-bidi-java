package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.json.JsonInput;

import java.util.Map;

public class StackFrame {

    private final String url;
    private final String functionName;
    private final int lineNumber;
    private final int columnNumber;

    private StackFrame(String scriptUrl, String function, int lineNumber, int columnNumber) {
        this.url = scriptUrl;
        this.functionName = function;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public String getUrl() {
        return url;
    }

    public String getFunctionName() {
        return functionName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public static StackFrame fromJson(JsonInput input) {
        String url = null;
        String functionName = null;
        int lineNumber = 0;
        int columnNumber = 0;

        input.beginObject();
        while (input.hasNext()) {
            switch (input.nextName()) {
                case "url":
                    url = input.read(String.class);
                    break;

                case "functionName":
                    functionName = input.read(String.class);
                    break;

                case "lineNumber":
                    lineNumber = input.read(Integer.class);
                    break;

                case "columnNumber":
                    columnNumber = input.read(Integer.class);
                    break;

                default:
                    input.skipValue();
                    break;
            }
        }

        input.endObject();

        return new StackFrame(url, functionName, lineNumber, columnNumber);
    }

    private Map<String, Object> toJson() {
        return Map.of(
                "url", url,
                "functionName", functionName,
                "lineNumber", lineNumber,
                "columnNumber", columnNumber);
    }
}

