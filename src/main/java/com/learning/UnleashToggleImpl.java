package com.learning;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import jakarta.inject.Singleton;

@Singleton
public class UnleashToggleImpl {

    private final String apiKey = "";
    private final String unleashUrl = "http://localhost:4242/api/";
    private final String appName = "petstore-micronaut";
    private final String instanceID = "development";
    private Unleash instance;


    public Unleash getInstance() {
        if (instance == null) {
            var config = UnleashConfig.builder()
                    .apiKey(apiKey)
                    .appName(appName)
                    .instanceId(instanceID)
                    .unleashAPI(unleashUrl)
                    .fetchTogglesInterval(10)
                    .build();
            instance = new DefaultUnleash(config);
        }

        return instance;

    }
}
