package com.junction.vk.configuration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    private final TaskScheduler taskScheduler;

    public RestTemplateConfiguration(@Qualifier(ThreadPoolConfiguration.TASK_SCHEDULER) TaskScheduler taskScheduler) {
        this.taskScheduler = Objects.requireNonNull(taskScheduler, "Task scheduler can't be null.");
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        // Try with resource close scheduler before create bean...
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(10);
        connectionManager.setValidateAfterInactivity(10_000);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setConnectionManager(connectionManager).setConnectionManagerShared(true).build());
        httpRequestFactory.setConnectionRequestTimeout(10_000);
        httpRequestFactory.setConnectTimeout(10_000);
        httpRequestFactory.setReadTimeout(10_000);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        taskScheduler.scheduleWithFixedDelay(() -> {
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
        }, 10_000);
        return restTemplate;
    }
}
