package org.openqa.selenium.remote.bidi;

import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidirectional.script.CallFunctionParameters;
import org.openqa.selenium.bidirectional.script.ContextTarget;
import org.openqa.selenium.bidirectional.script.EvaluateResult;
import org.openqa.selenium.bidirectional.script.EvaluateResultSuccess;
import org.openqa.selenium.bidirectional.script.ScriptModule;
import org.openqa.selenium.bidirectional.script.StringValue;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.Response;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class GetTitle implements BiFunction<Connection, Command, Response> {
    private final ScriptModule scriptModule = new ScriptModule();
    private final AtomicReference<BrowsingContext> currentBrowsingContext;
    private final AtomicReference<Duration> scriptTimeout;

    public GetTitle(AtomicReference<BrowsingContext> currentBrowsingContext, AtomicReference<Duration> scriptTimeout) {
        this.currentBrowsingContext = currentBrowsingContext;
        this.scriptTimeout = scriptTimeout;
    }

    @Override
    public Response apply(Connection connection, Command command) {
        org.openqa.selenium.bidi.Command<EvaluateResult> callFunction = scriptModule.callFunction(
                new CallFunctionParameters(
                        "() => {return document.title;}",
                        true,
                        new ContextTarget(currentBrowsingContext.get().toString())));
        EvaluateResult evalResult = connection.sendAndWait(callFunction, scriptTimeout.get());
        if (evalResult instanceof EvaluateResultSuccess) {
            StringValue value = (StringValue) ((EvaluateResultSuccess) evalResult).getResult();
            Response response = new Response();
            response.setState("success");
            response.setValue(value.toString());
            return response;
        }
        throw new UnsupportedOperationException("Don't know how to handle evaluate result: " + evalResult);
    }
}
