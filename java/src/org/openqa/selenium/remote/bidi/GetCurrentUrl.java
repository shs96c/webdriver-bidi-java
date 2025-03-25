package org.openqa.selenium.remote.bidi;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContextInfo;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContextModule;
import org.openqa.selenium.bidirectional.browsingcontext.GetTreeParameters;
import org.openqa.selenium.bidirectional.browsingcontext.GetTreeResult;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.Response;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class GetCurrentUrl implements BiFunction<Connection, Command, Response> {
    private final BrowsingContextModule browsingContextModule = new BrowsingContextModule();
    private final AtomicReference<BrowsingContext> currentBrowsingContext;
    private final AtomicReference<Duration> scriptTimeout;

    public GetCurrentUrl(AtomicReference<BrowsingContext> currentBrowsingContext, AtomicReference<Duration> scriptTimeout) {
        this.currentBrowsingContext = currentBrowsingContext;
        this.scriptTimeout = scriptTimeout;
    }

    @Override
    public Response apply(Connection connection, Command command) {
        org.openqa.selenium.bidi.Command<GetTreeResult> getTreeCommand = browsingContextModule.getTree(
                new GetTreeParameters(
                        1,
                        currentBrowsingContext.get()
                ));
        GetTreeResult getTreeResult = connection.sendAndWait(getTreeCommand, scriptTimeout.get());
        Response response = new Response();
        response.setState("success");
        BrowsingContextInfo info = getTreeResult.getContexts().stream()
                .filter(bci -> !bci.getContext().equals(currentBrowsingContext.get()))
                .findFirst()
                .orElseThrow(() -> new WebDriverException("Oh noes!"));
        response.setValue(info.getUrl());
        return response;
    }
}
