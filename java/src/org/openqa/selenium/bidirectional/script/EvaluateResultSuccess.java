package org.openqa.selenium.bidirectional.script;

public class EvaluateResultSuccess extends EvaluateResult {

    private final Realm realm;
    private final RemoteValue value;

    public EvaluateResultSuccess(Realm realm, RemoteValue value) {
        this.realm = realm;
        this.value = value;
    }

    public Realm getRealm() {
        return this.realm;
    }

    public RemoteValue getResult() {
        return this.value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EvaluateResultSuccess{");
        sb.append("realm=").append(realm);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
