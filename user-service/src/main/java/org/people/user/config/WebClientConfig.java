package org.people.user.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties
public class WebClientConfig {
    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .codecs(
                        configurer -> {
                            configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10);
                            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
                            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
                        })
                .defaultHeaders(
                        httpHeaders ->
                                httpHeaders.setAccept(
                                        List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED)))
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .responseTimeout(java.time.Duration.ofMinutes(2))
                                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000)
                                        .doOnConnected(
                                                connection ->
                                                        connection
                                                                .addHandlerLast(new ReadTimeoutHandler(2, TimeUnit.MINUTES))
                                                                .addHandlerLast(new WriteTimeoutHandler(2, TimeUnit.MINUTES)))))
                .build();
    }
}
