package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.json.JsonInput;

import java.util.Map;

public class ExceptionDetails {

    private final long columnNumber;
    private final RemoteValue exception;
    private final long lineNumber;
    private final StackTrace stackTrace;
    private final String text;

    public ExceptionDetails(
            long columnNumber,
            RemoteValue exception,
            long lineNumber,
            StackTrace stackTrace,
            String text) {
        this.columnNumber = columnNumber;
        this.exception = exception;
        this.lineNumber = lineNumber;
        this.stackTrace = stackTrace;
        this.text = text;
    }

    public static ExceptionDetails fromJson(JsonInput input) {
        long columnNumber = 0L;
        RemoteValue exception = null;
        long lineNumber = 0L;
        StackTrace stackTrace = null;
        String text = null;

        input.beginObject();
        while (input.hasNext()) {
            switch (input.nextName()) {
                case "columnNumber":
                    columnNumber = input.read(Long.class);
                    break;

                case "exception":
                    exception = input.read(RemoteValue.class);
                    break;

                case "lineNumber":
                    lineNumber = input.read(Long.class);
                    break;

                case "stackTrace":
                    stackTrace = input.read(StackTrace.class);
                    break;

                case "text":
                    text = input.read(String.class);
                    break;

                default:
                    input.skipValue();
                    break;
            }
        }

        input.endObject();

        return new ExceptionDetails(columnNumber, exception, lineNumber, stackTrace, text);
    }

    private Map<String, Object> toJson() {
        return Map.of(
                "columnNumber", this.columnNumber,
                "exception", this.exception,
                "lineNumber", this.lineNumber,
                "stacktrace", this.stackTrace,
                "text", this.text);
    }
}
