package org.openqa.selenium.example.bidi;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.bidi.BidiCommandExecutor;
import org.openqa.selenium.remote.http.ClientConfig;

import java.net.URI;

public class Main {
    public static void main(String... args) {
        RemoteWebDriver driver = (RemoteWebDriver) RemoteWebDriver.builder()
                .oneOf(new ChromeOptions().enableBiDi())
                .build();

        String rawUri = (String) driver.getCapabilities().getCapability("webSocketUrl");
        URI uri = URI.create(rawUri);
        ClientConfig clientConfig = ClientConfig.defaultConfig().baseUri(uri);

        BidiCommandExecutor executor = BidiCommandExecutor.create(clientConfig);
        WebDriver bidiDriver = new RemoteWebDriver(executor, new ChromeOptions().enableBiDi()) {
            @Override
            protected void startSession(Capabilities capabilities) {
                // no-op
            }
        };
        bidiDriver.get("https://www.rocketpoweredjetpants.com/2023/09/a-new-approach-to-ci/");
        System.err.printf("Title of %s is %s%n", bidiDriver.getCurrentUrl(), bidiDriver.getTitle());

        driver.quit();
    }
}