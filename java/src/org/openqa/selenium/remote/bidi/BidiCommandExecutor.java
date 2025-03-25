package org.openqa.selenium.remote.bidi;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.bidi.Command;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContextInfo;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContextModule;
import org.openqa.selenium.bidirectional.browsingcontext.GetTreeParameters;
import org.openqa.selenium.bidirectional.browsingcontext.GetTreeResult;
import org.openqa.selenium.bidirectional.browsingcontext.NavigationParameters;
import org.openqa.selenium.bidirectional.browsingcontext.NavigationResult;
import org.openqa.selenium.bidirectional.browsingcontext.ReadinessState;
import org.openqa.selenium.bidirectional.script.CallFunctionParameters;
import org.openqa.selenium.bidirectional.script.ContextTarget;
import org.openqa.selenium.bidirectional.script.EvaluateResult;
import org.openqa.selenium.bidirectional.script.EvaluateResultSuccess;
import org.openqa.selenium.bidirectional.script.RemoteValue;
import org.openqa.selenium.bidirectional.script.ScriptModule;
import org.openqa.selenium.bidirectional.script.StringValue;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

public class BidiCommandExecutor implements CommandExecutor {

    private final static BrowsingContextModule browsingContextModule = new BrowsingContextModule();
    private final static ScriptModule scriptModule = new ScriptModule();
    private final SessionId sessionId = new SessionId(UUID.randomUUID());
    private final Connection connection;
    private BrowsingContext currentBrowsingContext;
    private Duration navigationTimeout = Duration.ofMillis(300_000);
    private Duration scriptTimeout = Duration.ofMillis(30_000);

    private BidiCommandExecutor(Connection connection, BrowsingContext context) {
        this.connection = connection;
        currentBrowsingContext = Require.nonNull("Browsing context", context);
    }

    public static BidiCommandExecutor create(ClientConfig config) {
        HttpClient client = HttpClient.Factory.createDefault().createClient(config);

        Connection connection = new Connection(client, config.baseUri().toString());

        BrowsingContextModule module = new BrowsingContextModule();
        Command<GetTreeResult> command = module.getTree(new GetTreeParameters(0, null));
        GetTreeResult result = connection.sendAndWait(command, Duration.ofMillis(300_000));

        return new BidiCommandExecutor(connection, result.getContexts().iterator().next().getContext());
    }

    @Override
    public Response execute(org.openqa.selenium.remote.Command command) throws IOException {
        switch (command.getName()) {
            case DriverCommand.GET:
                Command<NavigationResult> cmd = browsingContextModule.navigate(
                        new NavigationParameters(
                                currentBrowsingContext,
                                (String) command.getParameters().get("url"),
                                ReadinessState.COMPLETE));
                try {
                    connection.sendAndWait(cmd, navigationTimeout);
                    Response response = new Response();
                    response.setState("success");
                    return response;
                } catch (TimeoutException e) {
                    Response response = new Response(sessionId);
                    response.setState("timeout");
                    return response;
                }

            case DriverCommand.GET_CURRENT_URL:
                Command<GetTreeResult> getTreeCommand = browsingContextModule.getTree(
                        new GetTreeParameters(
                                1,
                                currentBrowsingContext
                        ));
                GetTreeResult getTreeResult = connection.sendAndWait(getTreeCommand, navigationTimeout);
                Response response = new Response();
                response.setState("success");
                BrowsingContextInfo info = getTreeResult.getContexts().stream()
                        .filter(bci -> !bci.getContext().equals(currentBrowsingContext))
                        .findFirst()
                        .orElseThrow(() -> new WebDriverException("Oh noes!"));
                response.setValue(info.getUrl());
                return response;

            case DriverCommand.GET_TITLE:
                Command<EvaluateResult> callFunction = scriptModule.callFunction(
                        new CallFunctionParameters(
                                "() => {return document.title;}",
                                true,
                                new ContextTarget(currentBrowsingContext.toString())));
                EvaluateResult evalResult = connection.sendAndWait(callFunction, scriptTimeout);
                if (evalResult instanceof EvaluateResultSuccess) {
                    StringValue value = (StringValue) ((EvaluateResultSuccess) evalResult).getResult();
                    Response response1 = new Response();
                    response1.setState("success");
                    response1.setValue(value.toString());
                    return response1;
                }
                throw new UnsupportedOperationException("Don't know how to handle evaluate result: " + evalResult);

            default:
                throw new UnsupportedCommandException(command.getName());
        }
    }
}