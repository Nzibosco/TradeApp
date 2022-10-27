package com.vanderbilt.tradeAPI.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Configuration class to get Binance API client from the Binance API connector
 */

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
public class BinanceApiConfig implements EnvironmentAware {
    public static Environment env;

    public BinanceApiRestClient binanceClient (){
        BinanceApiClientFactory factory = BinanceApiClientFactory
                .newInstance(env.getProperty("API_KEY"), env.getProperty("SECRET_KEY"), false, false);
        return factory.newRestClient();
    }

    @Override
    public void setEnvironment(Environment environment) {
        BinanceApiConfig.env = environment;
    }
}
