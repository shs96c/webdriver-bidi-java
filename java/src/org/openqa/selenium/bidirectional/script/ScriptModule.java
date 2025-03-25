package org.openqa.selenium.bidirectional.script;

import org.openqa.selenium.bidi.Command;

public class ScriptModule {
    public Command<EvaluateResult> callFunction(CallFunctionParameters parameters) {
        return new Command<>(
                "script.callFunction",
                parameters.asMap(),
                EvaluateResult.class);
    }
}