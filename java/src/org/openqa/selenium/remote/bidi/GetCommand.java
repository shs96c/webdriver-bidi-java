package org.openqa.selenium.remote.bidi;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.bidi.Command;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContextModule;
import org.openqa.selenium.bidirectional.browsingcontext.NavigationParameters;
import org.openqa.selenium.bidirectional.browsingcontext.NavigationResult;
import org.openqa.selenium.bidirectional.browsingcontext.ReadinessState;
import org.openqa.selenium.remote.Response;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

class GetCommand implements BiFunction<Connection, org.openqa.selenium.remote.Command, Response> {
    private final BrowsingContextModule browsingContextModule = new BrowsingContextModule();
    private final AtomicReference<BrowsingContext> currentBrowsingContext;
    private final AtomicReference<Duration> navigationTimeout;

    public GetCommand(
            AtomicReference<BrowsingContext> currentBrowsingContext,
            AtomicReference<Duration> navigationTimeout) {
        this.currentBrowsingContext = currentBrowsingContext;
        this.navigationTimeout = navigationTimeout;
    }

    @Override
    public Response apply(Connection connection, org.openqa.selenium.remote.Command command) {
        Command<NavigationResult> cmd = browsingContextModule.navigate(
                new NavigationParameters(
                        currentBrowsingContext.get(),
                        (String) command.getParameters().get("url"),
                        ReadinessState.COMPLETE));
        try {
            connection.sendAndWait(cmd, navigationTimeout.get());
            Response response = new Response();
            response.setState("success");
            return response;
        } catch (TimeoutException e) {
            Response response = new Response();
            response.setState("timeout");
            return response;
        }
    }
}
