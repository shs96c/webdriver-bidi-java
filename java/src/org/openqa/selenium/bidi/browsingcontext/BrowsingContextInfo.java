package org.openqa.selenium.bidi.browsingcontext;

import org.openqa.selenium.bidi.browser.ClientWindow;
import org.openqa.selenium.bidi.browser.UserContext;
import org.openqa.selenium.json.JsonInput;
import org.openqa.selenium.json.TypeToken;

import java.util.Set;

public class BrowsingContextInfo {

    private final Set<BrowsingContextInfo> children;
    private final ClientWindow clientWindow;
    private final BrowsingContext context;
    private final BrowsingContext originalOpener;
    private final String url;
    private final Object userContext;
    private final BrowsingContext parent;

    public BrowsingContextInfo(
            Set<BrowsingContextInfo> children,
            ClientWindow clientWindow,
            BrowsingContext context,
            BrowsingContext originalOpener,
            String url,
            UserContext userContext,
            BrowsingContext parent) {
        this.children = Set.copyOf(children);
        this.clientWindow = clientWindow;
        this.context = context;
        this.originalOpener = originalOpener;
        this.url = url;
        this.userContext = userContext;
        this.parent = parent;
    }

    public Set<BrowsingContextInfo> getChildren() {
        return children;
    }

    public BrowsingContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BrowsingContextInfo{");
        sb.append("children=").append(children);
        sb.append(", clientWindow=").append(clientWindow);
        sb.append(", context=").append(context);
        sb.append(", originalOpener=").append(originalOpener);
        sb.append(", url='").append(url).append('\'');
        sb.append(", userContext=").append(userContext);
        sb.append(", parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }

    private static BrowsingContextInfo fromJson(JsonInput input) {
        Set<BrowsingContextInfo> children = Set.of();
        ClientWindow clientWindow = null;
        BrowsingContext context = null;
        BrowsingContext originalOpener = null;
        BrowsingContext parent = null;
        String url = null;
        UserContext userContext = null;

        input.beginObject();
        while (input.hasNext()) {
            switch (input.nextName()) {
                case "children":
                    children = input.read(new TypeToken<Set<BrowsingContextInfo>>() {
                    }.getType());
                    break;

                case "clientWindow":
                    clientWindow = new ClientWindow(input.nextString());
                    break;

                case "context":
                    context = input.read(BrowsingContext.class);
                    break;

                case "originalOpener":
                    originalOpener = input.read(BrowsingContext.class);
                    break;

                case "parent":
                    parent = input.read(BrowsingContext.class);
                    break;

                case "url":
                    url = input.nextString();
                    break;

                case "userContext":
                    userContext = input.read(UserContext.class);
                    break;

                default:
                    input.skipValue();
                    break;
            }
        }
        input.endObject();

        return new BrowsingContextInfo(
                children,
                clientWindow,
                context,
                originalOpener,
                url,
                userContext,
                parent);
    }
}
