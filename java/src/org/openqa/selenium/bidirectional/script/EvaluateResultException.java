package org.openqa.selenium.bidirectional.script;

public class EvaluateResultException extends EvaluateResult {
    private final Realm realm;
    private final ExceptionDetails exceptionDetails;

    public EvaluateResultException(Realm realm, ExceptionDetails exceptionDetails) {
        this.realm = realm;
        this.exceptionDetails = exceptionDetails;
    }
}
