package com.choisong.flyon.forecast.config;

import com.choisong.flyon.forecast.infrastructure.MidLandForecastClient;
import com.choisong.flyon.forecast.infrastructure.MidTemperatureClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WeatherApiRestClientConfig {

    @Bean
    MidLandForecastClient midLandForecastClient() {
        RestClient restClient = RestClient.create();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(MidLandForecastClient.class);
    }

    @Bean
    MidTemperatureClient midTemperatureClient() {
        RestClient restClient = RestClient.create();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(MidTemperatureClient.class);
    }
}