package org.openqa.selenium.remote.bidi;

import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.bidi.Command;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidirectional.browsingcontext.BrowsingContextModule;
import org.openqa.selenium.bidirectional.browsingcontext.GetTreeParameters;
import org.openqa.selenium.bidirectional.browsingcontext.GetTreeResult;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class BidiCommandExecutor implements CommandExecutor {

    private final SessionId sessionId = new SessionId(UUID.randomUUID());
    private final Connection connection;

    private AtomicReference<Duration> navigationTimeout = new AtomicReference<>(Duration.ofMillis(300_000));
    private AtomicReference<Duration> scriptTimeout = new AtomicReference<>(Duration.ofMillis(30_000));
    private Map<String, BiFunction<Connection, org.openqa.selenium.remote.Command, Response>> bidiFunctions = new HashMap<>();

    private BidiCommandExecutor(Connection connection, BrowsingContext context) {
        this.connection = connection;
        AtomicReference<BrowsingContext> currentBrowsingContext = new AtomicReference<>(Require.nonNull("Browsing context", context));

        bidiFunctions.put(DriverCommand.GET, new GetCommand(currentBrowsingContext, navigationTimeout));
        bidiFunctions.put(DriverCommand.GET_CURRENT_URL, new GetCurrentUrl(currentBrowsingContext, scriptTimeout));
        bidiFunctions.put(DriverCommand.GET_TITLE, new GetTitle(currentBrowsingContext, scriptTimeout));
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
        BiFunction<Connection, org.openqa.selenium.remote.Command, Response> func = bidiFunctions.get(command.getName());
        if (func == null) {
            Response response = new Response();
            response.setState("unsupported operation");
            response.setValue(new UnsupportedCommandException(command.getName()));
            return response;
        }

        Response response = func.apply(connection, command);
        response.setSessionId(sessionId.toString());
        return response;
    }
}