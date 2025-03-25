package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.json.JsonInput;

public class EvaluateResult {

    private static EvaluateResult fromJson(JsonInput input) {
        String type = null;
        RemoteValue result = null;
        Realm realm = null;
        ExceptionDetails exceptionDetails = null;

        input.beginObject();
        while (input.hasNext()) {
            switch (input.nextName()) {
                case "exceptionDetails":
                    exceptionDetails = input.read(ExceptionDetails.class);
                    break;

                case "realm":
                    realm = input.read(Realm.class);
                    break;

                case "result":
                    result = input.read(RemoteValue.class);
                    break;

                case "type":
                    type = input.nextString();
                    break;

                default:
                    input.skipValue();
                    break;
            }
        }
        input.endObject();

        switch (type) {
            case "success":
                return new EvaluateResultSuccess(realm, result);
            case "exception":
                return new EvaluateResultException(realm, exceptionDetails);
            default:
                throw new IllegalArgumentException("Unknown evaluate result type: " + type);
        }
    }
}
