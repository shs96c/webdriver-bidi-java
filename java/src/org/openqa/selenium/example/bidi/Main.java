package org.openqa.selenium.example.bidi;

import org.openqa.selenium.bidi.Command;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContextModule;
import org.openqa.selenium.bidi.browsingcontext.GetTreeParameters;
import org.openqa.selenium.bidi.browsingcontext.GetTreeResult;
import org.openqa.selenium.bidi.browsingcontext.NavigationParameters;
import org.openqa.selenium.bidi.browsingcontext.NavigationResult;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URI;
import java.time.Duration;

public class Main {
    public static void main(String... args) {
        RemoteWebDriver driver = (RemoteWebDriver) RemoteWebDriver.builder()
                .oneOf(new ChromeOptions().enableBiDi())
                .build();

        SessionId sessionId = driver.getSessionId();
        String rawUri = (String) driver.getCapabilities().getCapability("webSocketUrl");
        URI uri = URI.create(rawUri);
        System.err.println(uri);

        ClientConfig clientConfig = ClientConfig.defaultConfig().baseUri(uri);
        HttpClient httpClient = HttpClient.Factory.createDefault().createClient(clientConfig);
        try (Connection connection = new Connection(httpClient, rawUri)) {
            BrowsingContextModule browsingContextModule = new BrowsingContextModule();

            Command<GetTreeResult> getTreeCommand = browsingContextModule.getTree(new GetTreeParameters(0, null));
            GetTreeResult getTreeResult = connection.sendAndWait(getTreeCommand, Duration.ofMinutes(1));

            System.err.println(getTreeResult.getContexts());

            // Grab the first context
            BrowsingContext context = getTreeResult.getContexts().iterator().next().getContext();

            Command<NavigationResult> navigationCommand = browsingContextModule.navigate(
                    new NavigationParameters(
                            context,
                            "http://rocketpoweredjetpants.com",
                            ReadinessState.COMPLETE));
            NavigationResult navigationResult = connection.sendAndWait(navigationCommand, Duration.ofMinutes(5));
            System.err.println("URL is now: " + navigationResult.getUrl());
        }

        driver.quit();
    }
}