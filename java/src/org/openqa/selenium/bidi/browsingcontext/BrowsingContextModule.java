package org.openqa.selenium.bidi.browsingcontext;

import org.openqa.selenium.bidi.Command;

public class BrowsingContextModule {
    public Command<GetTreeResult> getTree(GetTreeParameters parameters) {
        return new Command<>(
                "browsingContext.getTree",
                parameters.asMap(),
                GetTreeResult.class);
    }

    public Command<NavigationResult> navigate(NavigationParameters parameters) {
        return new Command<>(
                "browsingContext.navigate",
                parameters.asMap(),
                NavigationResult.class);
    }
}
