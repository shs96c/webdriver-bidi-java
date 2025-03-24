package org.openqa.selenium.bidi.browsingcontext;

import java.util.Collection;
import java.util.Set;

import org.openqa.selenium.json.JsonInput;
import org.openqa.selenium.json.TypeToken;

public class GetTreeResult {

    private final Set<BrowsingContextInfo> contexts;

    public GetTreeResult(Collection<BrowsingContextInfo> contexts) {
        this.contexts = Set.copyOf(contexts);
    }

    public Set<BrowsingContextInfo> getContexts() {
        return contexts;
    }

    private static GetTreeResult fromJson(JsonInput input) {
        Collection<BrowsingContextInfo> contexts = Set.of();

        input.beginObject();
        while (input.hasNext()) {
            switch (input.nextName()) {
                case "contexts":
                    contexts = input.read(new TypeToken<Set<BrowsingContextInfo>>(){}.getType());
                    break;

                default:
                    input.skipValue();
                    break;
            }
        }
        input.endObject();

        return new GetTreeResult(contexts);
    }
}
